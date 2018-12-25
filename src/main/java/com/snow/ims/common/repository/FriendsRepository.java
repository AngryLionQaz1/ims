package com.snow.ims.common.repository;

import com.snow.ims.common.pojo.Friends;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendsRepository extends JpaRepository<Friends,Long> {


    List<Friends> findByUserIdAndFriendId(Long userId, Long friendId);



}
