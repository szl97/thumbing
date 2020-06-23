package com.loserclub.pushdata.datacenter.data;

import com.loserclub.pushdata.datacenter.config.DataCenterConfig;
import com.loserclub.pushdata.datacenter.inbound.NodeToCenterInBoundDataFlowHandler;
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

/**
 * @author Stan Sai
 * @date 2020-06-22
 */

@Slf4j
@Component
@Data
public class DataFlowBootStrap {
    @Autowired
    private DataCenterConfig dataCenterConfig;

    private NioEventLoopGroup boss = new NioEventLoopGroup();

    private NioEventLoopGroup work = new NioEventLoopGroup();

    @Autowired
    private NodeToCenterInBoundDataFlowHandler nodeToCenterInBoundDataFlowHandler;

    public void init() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, work)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception{
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //拆包粘包问题和编码问题
                        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                        pipeline.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));

                        //空闲检测
                        pipeline.addLast("idleStateHandler", new IdleStateHandler(300, 0, 0));

                        //处理Node Server成功连接确认事件、心跳事件、推送消息事件
                        pipeline.addLast("handler", nodeToCenterInBoundDataFlowHandler);
                    }
                })
                .option(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_SNDBUF, 2048)
                .option(ChannelOption.SO_RCVBUF, 1024);
        bootstrap.bind(dataCenterConfig.getMessagePort()).sync();
        log.info("Data center successful! listening port: {}", dataCenterConfig.getMessagePort());

    }
}
