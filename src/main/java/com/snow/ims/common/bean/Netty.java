package com.snow.ims.common.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix ="netty" )
public class Netty {


    /**netty 监听端口*/
    private Integer port=2222;
    /**Socket参数，连接保活，默认值为False。启用该功能时，TCP会主动探测空闲连接的有效性。可以将此功能视为TCP的心跳机制，需要注意的是：默认的心跳间隔是7200s即2小时。Netty默认关闭该功能*/
    private Boolean keepalive=false;
    /**Socket参数，服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝*/
    private Integer backlog=1024;
   /**TCP参数，立即发送数据，默认值为Ture（Netty默认为True而操作系统默认为False）。该值设置Nagle算法的启用，改算法将小的碎片数据连接成更大的报文来最小化所发送的报文的数量，如果需要发送一些较小的报文，则需要禁用该算法。Netty默认禁用该算法，从而最小化报文传输延时。*/
   private Boolean nodelay=true;
   /**地址复用，默认值False。有四种情况可以使用：(1).当有一个有相同本地地址和端口的socket1处于TIME_WAIT状态时，而你希望启动的程序的socket2要占用该地址和端口，比如重启服务且保持先前端口。(2).有多块网卡或用IP Alias技术的机器在同一端口启动多个进程，但每个进程绑定的本地IP地址不能相同。(3).单个进程绑定相同的端口到多个socket上，但每个socket绑定的ip地址不同。(4).完全相同的地址和端口的重复绑定。但这只用于UDP的多播，不用于TCP。*/
   private Boolean reuseaddr=false;
   /**Socket参数，TCP数据发送缓冲区大小*/
   private Integer sndbuf=10485760;
   /**Socket参数，TCP数据接收缓冲区大小*/
   private Integer revbuf=10485760;
   /**读超时时间*/
   private Integer heart=180;
   /**主线线程数*/
   private Integer bossThread=1;
   /**工作线程数*/
   private Integer workerThread=2;
   /**指定netty ChannelInitializer bean 名称*/
   private String channelName="imChannelInitializer";



}
