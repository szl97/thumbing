package com.loserclub.pushdata.common.server;

import com.loserclub.pushdata.common.config.BaseAppConfig;
import com.loserclub.pushdata.common.inbund.MessageInBoundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * @author Stan Sai
 * @date 2020-07-10
 */
@Slf4j
@Data
@NoArgsConstructor
public abstract class BaseServerBootStrap<T extends MessageInBoundHandler, K extends BaseAppConfig> {
    @Autowired
    private K appConfig;

    private NioEventLoopGroup boss = new NioEventLoopGroup();

    private NioEventLoopGroup work = new NioEventLoopGroup();

    private ServerBootstrap bootstrap;

    private Object lock = new Object();

    @Autowired
    private T messageInBoundHandler;

    public void init() throws InterruptedException {
        if (bootstrap == null) {
            synchronized (lock) {
                if (bootstrap == null) {
                    ServerBootstrap bootstrap = new ServerBootstrap();
                    bootstrap.group(boss, work)
                            .channelFactory(NioServerSocketChannel::new)
                            .childHandler(new ChannelInitializer<Channel>() {
                                @Override
                                protected void initChannel(Channel socketChannel) throws Exception {
                                    ChannelPipeline pipeline = socketChannel.pipeline();
                                    //拆包粘包问题和编码问题
                                    pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                                    pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
                                    pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                                    pipeline.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));

                                    //空闲检测
                                    pipeline.addLast("idleStateHandler", new IdleStateHandler(300, 0, 0));

                                    //处理Node Server成功连接确认事件、心跳事件、推送消息事件
                                    pipeline.addLast("handler", messageInBoundHandler);
                                }
                            })
                            .option(ChannelOption.TCP_NODELAY, true)
                            .childOption(ChannelOption.SO_REUSEADDR, true)
                            .option(ChannelOption.SO_SNDBUF, 2048)
                            .option(ChannelOption.SO_RCVBUF, 1024);
                    bootstrap.bind(getPort()).sync();
                    success();
                }
            }
        }
    }

    protected abstract int getPort();

    protected abstract void success();
}
