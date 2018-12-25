package com.snow.ims.module.netty.im.pool;

import com.snow.ims.common.bean.UserChannel;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
/**链接管理*/
public class ConnectionPool {

    /**客户端*/
    private static Map<String, UserChannel> poolClient = new ConcurrentHashMap();
    private static Map<String,String> poolUser=new ConcurrentHashMap<>();


    /**根据地址获取 channel*/
    public static ChannelHandlerContext getA(String address){
        UserChannel uc=poolClient.get(address);
        if (uc==null)return null;
        return uc.getCht();
    }

    /**根据用户ID 获取channel*/
    public static ChannelHandlerContext get(String userId){
        String address=poolUser.get(userId);
        if (address==null)return null;
        UserChannel uc=poolClient.get(address);
        if (uc==null)return null;
        return uc.getCht();
    }

    /**添加 channel*/
    public static void add(String userId,ChannelHandlerContext cht){
        poolUser.put(userId,cht.channel().remoteAddress().toString());
        poolClient.put(cht.channel().remoteAddress().toString(),UserChannel.builder().userId(userId).cht(cht).build());
    }

    /**用户退出*/
    public static void remove(String userId){
        String address=poolUser.get(userId);
        if (address==null)return;
        UserChannel uc=poolClient.get(address);
        if (uc==null)return;
        poolClient.remove(address);
        poolUser.remove(userId);
    }

    /**掉线*/
    public static void removeA(String address){
        UserChannel uc=poolClient.get(address);
        if (uc==null)return;
        String userId=uc.getUserId();
        if (!poolUser.containsKey(userId))return;
        poolUser.remove(userId);
        poolClient.remove(address);
    }

    /**获取在线人*/
    public static List<UserChannel> getChannels() {
        List<UserChannel> channels = new ArrayList<>();
        Set<String> keys = poolClient.keySet();
        for (String key : keys) {
            channels.add(poolClient.get(key));
        }
        return channels;
    }

}
