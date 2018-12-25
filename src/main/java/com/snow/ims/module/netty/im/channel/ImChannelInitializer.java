package com.snow.ims.module.netty.im.channel;


import com.snow.ims.module.netty.im.handler.ImHandler;
import com.snow.ims.module.netty.message.ImProtobuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImChannelInitializer extends ChannelInitializer {


    @Autowired
    private ImHandler imHandler;


    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
        // 实体类传输数据，protobuf序列化
        ch.pipeline().addLast("decoder", new ProtobufDecoder(ImProtobuf.Message.getDefaultInstance()));
        //对protobuf协议的的消息头上加上一个长度为32的整形字段，用于标志这个消息的长度
        ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
        ch.pipeline().addLast("encoder", new ProtobufEncoder());
        ch.pipeline().addLast(imHandler);
    }







}
