package com.snow.ims.module.netty.im.service;

import com.snow.ims.common.pojo.User;
import com.snow.ims.common.pool.StandardThreadExecutor;
import com.snow.ims.common.util.StringUtils;
import com.snow.ims.module.netty.im.pool.ConnectionPool;
import com.snow.ims.module.netty.message.ImProtobuf;
import com.snow.ims.module.user.UserService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImService {


    @Autowired
    private Protobuf protobuf;
    @Autowired
    private StandardThreadExecutor standardThreadExecutor;
    @Autowired
    private UserService userService;

    /**回复好友申请*/
    public void user2User3(ImProtobuf.Message msg) {
        ImProtobuf.User2User3 u3=msg.getUser3();
        if (StringUtils.isNull(u3.getType()))return;
        if (u3.getType()==0)return;
        standardThreadExecutor.execute(()->userService.addFriend(u3));
        sendMsgUser2User(msg);
    }

    /**好友申请*/
    public void user2User2(User user,User toUser){
        sendMsgUser2User(protobuf.user2user2(user,toUser));
    }

    /**好友申请*/
    public void user2User2(ImProtobuf.Message msg){
        sendMsgUser2User(msg);
    }


    /**用户给好友发送消息*/
    public void user2User1(ImProtobuf.Message msg){
        sendMsgUser2User(msg);
    }

    /**用户退出*/
    public void userLogout(ImProtobuf.Message msg){
        String userId=msg.getLogin().getUserId();
        ConnectionPool.remove(userId);
        ConnectionPool.getChannels().stream().forEach(System.out::print);

    }

    /**获取掉线*/
    public void logout(ChannelHandlerContext ctx){
        ConnectionPool.getA(ctx.channel().remoteAddress().toString());
        ConnectionPool.getChannels().stream().forEach(System.out::print);

    }

    /**用户登录*/
    public void login(ImProtobuf.Message msg,ChannelHandlerContext ctx){
        String userId=msg.getLogin().getUserId();
        ConnectionPool.add(userId,ctx);
        ConnectionPool.getChannels().stream().forEach(System.out::print);

    }


    /**根据 用户2用户 发送消息*/
    public void sendMsgUser2User(ImProtobuf.Message msg){
        standardThreadExecutor.execute(()->{
            ImProtobuf.User2User1 o=msg.getUser1();
            ChannelHandlerContext ctx=ConnectionPool.get(o.getToUserId());
            if (ctx==null)return;
            ctx.writeAndFlush(msg);
        });
    }


}
