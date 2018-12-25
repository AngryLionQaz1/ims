package com.snow.ims.module.user;

import com.snow.ims.common.bean.Result;
import com.snow.ims.common.bean.Tips;
import com.snow.ims.common.pojo.Friends;
import com.snow.ims.common.pojo.Group;
import com.snow.ims.common.pojo.Role;
import com.snow.ims.common.pojo.User;
import com.snow.ims.common.repository.FriendsRepository;
import com.snow.ims.common.repository.GroupRepository;
import com.snow.ims.common.repository.RoleRepository;
import com.snow.ims.common.repository.UserRepository;
import com.snow.ims.common.util.PasswordEncoderUtils;
import com.snow.ims.config.security.SecurityContextHolder;
import com.snow.ims.config.token.JWTToken;
import com.snow.ims.module.netty.im.service.ImService;
import com.snow.ims.module.netty.message.ImProtobuf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.*;

import static com.snow.ims.common.bean.Result.fail;
import static com.snow.ims.common.bean.Result.success;

@Service
public class UserService {

    @Autowired
    private JWTToken jwtToken;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityContextHolder securityContextHolder;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private ImService imService;

    public Result login(String username, String password) {
        Optional<User> o = userRepository.findByUsername(username);
        if (!o.isPresent()) return fail(Tips.USER_NOT.msg);
        if (!PasswordEncoderUtils.decode(password, o.get().getPassword())) return fail(Tips.USER_NOT.msg);
        User user = o.get();
        user.setToken(jwtToken.createToken(String.valueOf(user.getId())));
        return success(user);
    }

    public Result register(String username, String password) {
        Optional<User> o = userRepository.findByUsername(username);
        if (o.isPresent()) return fail(Tips.USER_HAD.msg);
        Optional<Role> or = roleRepository.findById(1L);
        if (!or.isPresent()) return fail(Tips.ROLE_NOT.msg);
        return success(userRepository.save(User.builder()
                .username(username)
                .img("http://img1.imgtn.bdimg.com/it/u=927595719,207396172&fm=26&gp=0.jpg")
                .password(PasswordEncoderUtils.encode(password))
                .roles(Arrays.asList(or.get()))
                .createTime(LocalDateTime.now())
                .build()));
    }

    public Result friends() {
        User user = securityContextHolder.getUser();
        return success(user.getFriends());
    }

    public Result groups() {
        User user = securityContextHolder.getUser();
        return success(user.getGroups());
    }

    public Result createGroup(String groupName, String groupImg) {
        User user = securityContextHolder.getUser();
        Set<User> users = new HashSet();
        users.add(user);
        return success(groupRepository.save(
                Group.builder()
                        .adminId(user.getId())
                        .groupName(groupName)
                        .groupImg(groupImg)
                        .time(LocalDateTime.now())
                        .users(users)
                        .build()));
    }

    public Result addFriend(Long friendId) {
        User user = securityContextHolder.getUser();
        if (friendsRepository.findByUserIdAndFriendId(user.getId(), friendId).size() > 0) return fail(Tips.FRIEND_HAD.msg);
        Optional<User> friend = userRepository.findById(friendId);
        if (!friend.isPresent()) return fail(Tips.USER_NOT.msg);
        imService.user2User2(user, friend.get());
        return success();
    }

    public Result searchFriend(String name, Integer page, Integer pagesize) {
        if (page > 0) page--;
        Pageable pageable = PageRequest.of(page, pagesize, new Sort(Sort.Direction.DESC, "createTime"));
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.like(root.get("username").as(String.class),"%"+name+"%"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
            }
        };
      return success(userRepository.findAll(specification,pageable));
    }

    public Result deleteFriend(Long id) {
        Optional<Friends> o=friendsRepository.findById(id);
        if (!o.isPresent())return fail(Tips.FRIEND_NOT.msg);
        friendsRepository.delete(o.get());
        return success();
    }

    /**好友申请通过*/
    public void addFriend(ImProtobuf.User2User3 user3){
        Long userId = Long.valueOf(user3.getUserId());
        Long toUserId = Long.valueOf(user3.getToUserId());
        Optional<User> user = userRepository.findById(userId);
        Optional<User> toUser = userRepository.findById(toUserId);
        if (!user.isPresent()) return;
        if (!toUser.isPresent()) return;
        if (friendsRepository.findByUserIdAndFriendId(userId, toUserId).size() > 0) return;
        if (friendsRepository.findByUserIdAndFriendId(toUserId, userId).size() > 0) return;
        friendsRepository.saveAll(Arrays.asList(
                Friends.builder().uId(userId).friendId(toUserId).time(LocalDateTime.now()).user(user.get()).build(),
                Friends.builder().uId(toUserId).friendId(userId).time(LocalDateTime.now()).user(toUser.get()).build()
        ));
    }



}