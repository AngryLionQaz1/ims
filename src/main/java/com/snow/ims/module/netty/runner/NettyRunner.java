package com.snow.ims.module.netty.runner;

import com.snow.ims.config.netty.inter.BootstrapServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NettyRunner implements CommandLineRunner {

    @Autowired
    private BootstrapServer bootstrapServer;


    @Override
    public void run(String... args) throws Exception {
//      new Thread(()->bootstrapServer.start());
    }


}
