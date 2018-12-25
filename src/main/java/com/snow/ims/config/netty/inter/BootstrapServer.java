package com.snow.ims.config.netty.inter;

import com.snow.ims.common.bean.Netty;

/**主程序*/
public interface BootstrapServer {

    /**初始化*/
    void init(Netty netty);

    /**开启*/
    void start();

    /**停止*/
    void shutdown();




}
