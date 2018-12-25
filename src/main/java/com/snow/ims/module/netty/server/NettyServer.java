package com.snow.ims.module.netty.server;

import com.snow.ims.common.bean.Netty;
import com.snow.ims.common.util.IpUtils;
import com.snow.ims.config.netty.inter.AbstractBootstrapServer;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class NettyServer extends AbstractBootstrapServer {

    private Netty netty;
    private ChannelInitializer channelInitializer;
    public NettyServer(Netty netty, ChannelInitializer channelInitializer) {
        this.netty = netty;
        this.channelInitializer = channelInitializer;
    }

    @Override
    public void start() {
        init(netty);
        bootstrap.group(bossGroup, workGroup)
                .channel(useEpoll()? EpollServerSocketChannel.class: NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, netty.getReuseaddr())
                .option(ChannelOption.SO_BACKLOG, netty.getBacklog())
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.SO_RCVBUF, netty.getRevbuf())
                .childHandler(channelInitializer)
                .childOption(ChannelOption.TCP_NODELAY, netty.getNodelay())
                .childOption(ChannelOption.SO_KEEPALIVE, netty.getKeepalive())
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
             bootstrap.bind(IpUtils.getHost(), netty.getPort()).addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                log.info("服务端启动成功【" + IpUtils.getHost() + ":" + netty.getPort() + "】");
                System.out.println("服务端启动成功【" + IpUtils.getHost() + ":" + netty.getPort() + "】");
            }else{
                log.info("服务端启动失败【" + IpUtils.getHost() + ":" + netty.getPort() + "】");
                System.out.println("服务端启动失败【" + IpUtils.getHost() + ":" + netty.getPort() + "】");}
        });
    }



}
