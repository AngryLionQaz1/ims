package com.snow.ims.config.netty.config;

import com.snow.ims.common.bean.Netty;
import com.snow.ims.common.util.SpringBeanUtils;
import com.snow.ims.config.netty.inter.BootstrapServer;
import com.snow.ims.module.netty.server.NettyServer;
import io.netty.channel.ChannelInitializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass
@EnableConfigurationProperties({Netty.class})
public class NettyConfig {


    @Bean(initMethod = "start", destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public BootstrapServer bootstrapServer(Netty netty){
        return new NettyServer(netty,SpringBeanUtils.getBean(netty.getChannelName(), ChannelInitializer.class));
    }




}
