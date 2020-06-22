package com.loserclub.pushdata.datacenter.monitors;

import com.loserclub.pushdata.datacenter.config.DataCenterConfig;
import com.loserclub.pushdata.datacenter.inbound.NodeToCenterInBoundMonitorHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Data
public class ServerConnectMonitorBootStrap {

    @Autowired
    private DataCenterConfig dataCenterConfig;

    private NioEventLoopGroup boss = new NioEventLoopGroup();

    private NioEventLoopGroup work = new NioEventLoopGroup();

    @Autowired
    private NodeToCenterInBoundMonitorHandler nodeToCenterInBoundMonitorHandler;

    public void init() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, work)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception{
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //拆包粘包问题
                        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));

                        //编码解码
                        pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                        pipeline.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));

                        //空闲检测
                        pipeline.addLast("idleStateHandler", new IdleStateHandler(300, 0, 0));

                        //处理Node 心跳事件、node server与客户端之间连接的建立和删除事件
                        pipeline.addLast("handler", nodeToCenterInBoundMonitorHandler);
                    }
                })
                .option(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_SNDBUF, 2048)
                .option(ChannelOption.SO_RCVBUF, 1024);
        bootstrap.bind(dataCenterConfig.getPort()).sync();
        log.info("Data center successful! listening port: {}", dataCenterConfig.getPort());

    }

}
