package com.snow.ims.module.netty.im.handler;


import com.snow.ims.module.netty.im.service.ImService;
import com.snow.ims.module.netty.im.service.Protobuf;
import com.snow.ims.module.netty.message.ImProtobuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class ImHandler extends SimpleChannelInboundHandler<ImProtobuf.Message> {

    @Autowired
    private ImService imService;
    @Autowired
    private Protobuf protobuf;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ImProtobuf.Message msg) throws Exception {
        protobuf.switchMsg(msg,ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        imService.logout(ctx);
        log.info("客户端掉线:"+ctx.channel().remoteAddress());
        ctx.channel();
//        cause.printStackTrace();
        ctx.close();
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("新客户端连接接入。。。"+ctx.channel().remoteAddress());
        ctx.fireChannelActive();
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        imService.logout(ctx);
        log.info("客户端失去链接:"+ctx.channel().remoteAddress());
        ctx.fireChannelInactive();
    }







}
