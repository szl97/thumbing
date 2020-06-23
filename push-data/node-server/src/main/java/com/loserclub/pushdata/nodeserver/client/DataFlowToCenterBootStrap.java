package com.loserclub.pushdata.nodeserver.client;

import com.loserclub.pushdata.common.utils.ip.IpUtils;
import com.loserclub.pushdata.nodeserver.messages.Confirm;
import com.loserclub.pushdata.common.Infos.DataCenterInfo;
import com.loserclub.pushdata.common.constants.AttributeEnum;
import com.loserclub.pushdata.nodeserver.channel.DataFlowChannelManager;
import com.loserclub.pushdata.nodeserver.config.NodeServerConfig;
import com.loserclub.pushdata.nodeserver.inbound.NodeToCenterInBoundDataFlowHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class DataFlowToCenterBootStrap {
    @Autowired
    private NodeServerConfig nodeServerConfig;

    private NioEventLoopGroup group = new NioEventLoopGroup();

    private Bootstrap bootstrap = new Bootstrap();

    @Autowired
    private DataFlowChannelManager channelManager;

    @Autowired
    NodeToCenterInBoundDataFlowHandler nodeToCenterInBoundDataFlowHandler;

    @PostConstruct
    public void init() throws InterruptedException {
        bootstrap.group(group)
                .handler(new ChannelInitializer<SocketChannel>() {
                             @Override
                             protected void initChannel(SocketChannel socketChannel) throws Exception {
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
                         }
                ).option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_SNDBUF, 2048)
                .option(ChannelOption.SO_RCVBUF, 1024);
        bootstrap.bind(nodeServerConfig.getMessagePort()).sync();
        log.info("node server successful! listening port: {}", nodeServerConfig.getMessagePort());
    }

    public void Connect(DataCenterInfo info, int retry, int times){
        bootstrap.connect(info.getIp(),info.getPort())
                .addListener(future -> {
                    if(future.isSuccess()){
                        Channel channel = ((ChannelFuture)future).channel();
                        List<AttributeEnum> attributeEnums = new ArrayList<>();
                        attributeEnums.add(AttributeEnum.CHANNEL_ATTR_DATACENTER);
                        channelManager.bindAttributes(info.getName(), channel, attributeEnums);
                        String ip = IpUtils.internetIp();
                        channel.writeAndFlush(
                                Confirm.builder()
                                        .name(nodeServerConfig.getName())
                                        .nodeIpWithPort(ip+":"+nodeServerConfig.getMessagePort())
                                .build().encode()
                        );
                    }
                    else if(retry == 0){
                        //无法连接到Data Center
                    }
                    else{
                        bootstrap.config().group().schedule(()->{Connect(info, retry-1, times+1);}, 1<<times, TimeUnit.SECONDS);
                    }
                });
    }

    public void DisConnect(DataCenterInfo info){
        channelManager.removeChannel(info.getName());
    }
}
