package com.snow.ims.module.netty.im.service;

import com.snow.ims.common.pojo.User;
import com.snow.ims.module.netty.message.ImProtobuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.snow.ims.module.netty.message.ImProtobuf.Message.DataType.*;

@Component
public class Protobuf {


    @Autowired
    private ImService imService;


    /**消息转换*/
    public void switchMsg(ImProtobuf.Message msg, ChannelHandlerContext ctx){
        if (msg.getType()==PING)return;
        switch (msg.getType()){
            case LOGIN:
                imService.login(msg,ctx);
                break;
            case LOGOUT:
                imService.userLogout(msg);
                break;
            case USER_TO_USER_1:
                imService.user2User1(msg);
            case USER_TO_USER_2:
                imService.user2User2(msg);
                break;
            case USER_TO_USER_3:
                imService.user2User3(msg);
                break;
                default:
                    return;

        }
    }



    /**回复好友申请 type 0 拒绝 1 同意*/
    public ImProtobuf.Message user2user3(User user, User toUser, Integer type){
        return ImProtobuf.Message
                .newBuilder()
                .setType(USER_TO_USER_3)
                .setUser1(
                        ImProtobuf.User2User1
                                .newBuilder()
                                .setUserId(String.valueOf(user.getId()))
                                .setUserName(user.getUsername())
                                .setUserImg(user.getImg())
                                .setToUserId(String.valueOf(toUser.getId()))
                                .setToUserName(toUser.getUsername())
                                .setToUserImg(toUser.getImg())
                                .setType(type)
                                .build()
                )
                .build();
    }


    /**添加好友*/
    public ImProtobuf.Message user2user2(User user,User toUser){
        return ImProtobuf.Message
                .newBuilder()
                .setType(USER_TO_USER_2)
                .setUser1(
                        ImProtobuf.User2User1
                                .newBuilder()
                                .setUserId(String.valueOf(user.getId()))
                                .setUserName(user.getUsername())
                                .setUserImg(user.getImg())
                                .setToUserId(String.valueOf(toUser.getId()))
                                .setToUserName(toUser.getUsername())
                                .setToUserImg(toUser.getImg())
                                .build()
                )
                .build();
    }

    /**用户发送消息给用户 type 文本信息 0、图片1、文件2、视频3*/
    public ImProtobuf.Message user2user1(User user,User toUser,String content,Integer type){
        return ImProtobuf.Message
                .newBuilder()
                .setType(USER_TO_USER_1)
                .setUser1(
                        ImProtobuf.User2User1
                                .newBuilder()
                                .setUserId(String.valueOf(user.getId()))
                                .setUserName(user.getUsername())
                                .setUserImg(user.getImg())
                                .setToUserId(String.valueOf(toUser.getId()))
                                .setToUserName(toUser.getUsername())
                                .setToUserImg(toUser.getImg())
                                .setContent(content)
                                .setType(type)
                                .build()
                )
                .build();
    }


    /**退出*/
    public ImProtobuf.Message logout(String userId){
        return ImProtobuf.Message
                .newBuilder()
                .setType(LOGOUT)
                .setLogout(
                        ImProtobuf.Logout
                                .newBuilder()
                                .setUserId(userId)
                                .build()
                )
                .build();
    }


    /**登录*/
    public ImProtobuf.Message login(String userId){
        return ImProtobuf.Message
                .newBuilder()
                .setType(LOGIN)
                .setLogin(
                        ImProtobuf.Login
                                .newBuilder()
                                .setUserId(userId)
                                .build()
                )
                .build();
    }


    /**心跳*/
    public ImProtobuf.Message ping(){
        return ImProtobuf.Message.newBuilder().setType(PING).build();
    }










}
