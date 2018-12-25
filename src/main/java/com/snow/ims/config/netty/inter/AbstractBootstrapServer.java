package com.snow.ims.config.netty.inter;

import com.snow.ims.common.bean.Netty;
import com.snow.ims.common.util.IpUtils;
import com.snow.ims.common.util.RemotingUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public abstract class AbstractBootstrapServer implements BootstrapServer {

    public ServerBootstrap bootstrap;// 启动辅助类
    public EventLoopGroup bossGroup;
    public EventLoopGroup workGroup;


    @Override
    public void init(Netty netty) {
        bootstrap= new ServerBootstrap();
        if(useEpoll()){
            bossGroup = new EpollEventLoopGroup(netty.getBossThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);
                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_BOSS_" + index.incrementAndGet());
                }
            });
            workGroup = new EpollEventLoopGroup(netty.getWorkerThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);
                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_WORK_" + index.incrementAndGet());
                }
            });

        }
        else {
            bossGroup = new NioEventLoopGroup(netty.getBossThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);
                public Thread newThread(Runnable r) {
                    return new Thread(r, "BOSS_" + index.incrementAndGet());
                }
            });
            workGroup = new NioEventLoopGroup(netty.getWorkerThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);
                public Thread newThread(Runnable r) {
                    return new Thread(r, "WORK_" + index.incrementAndGet());
                }
            });
        }
    }



    /**
     * 关闭资源
     */
    public void shutdown() {
        if(workGroup!=null && bossGroup!=null ){
            try {
                bossGroup.shutdownGracefully().sync();// 优雅关闭
                workGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                log.info("服务端关闭资源失败【" + IpUtils.getHost()+"】");
                System.out.println("服务端关闭资源失败【" + IpUtils.getHost() + "】");
            }
        }
    }
    public boolean useEpoll() {
        return RemotingUtil.isLinuxPlatform()
                && Epoll.isAvailable();
    }
}
