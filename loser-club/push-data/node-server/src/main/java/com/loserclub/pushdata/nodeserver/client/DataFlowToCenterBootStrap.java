package com.loserclub.pushdata.nodeserver.client;

import com.loserclub.pushdata.common.message.Confirm;
import com.loserclub.shared.utils.ip.*;
import com.loserclub.pushdata.common.Infos.DataCenterInfo;
import com.loserclub.pushdata.common.constants.AttributeEnum;
import com.loserclub.pushdata.nodeserver.channel.DataFlowChannelManager;
import com.loserclub.pushdata.nodeserver.config.NodeServerConfig;
import com.loserclub.pushdata.nodeserver.inbound.NodeToCenterInBoundDataFlowHandler;
import com.loserclub.pushdata.nodeserver.server.DeviceServerBootStrap;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 作为消息中心的客户端将设备客户端的消息发送给data-center
 *
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

    @Autowired
    private DataFlowChannelManager channelManager;

    @Autowired
    NodeToCenterInBoundDataFlowHandler nodeToCenterInBoundDataFlowHandler;

    @Autowired
    DeviceServerBootStrap deviceServerBootStrap;

    public Bootstrap init(String ip, int port) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .remoteAddress(ip, port)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
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
                                 pipeline.addLast("handler", nodeToCenterInBoundDataFlowHandler);

                             }
                         }
                )
                .option(ChannelOption.TCP_NODELAY, true);
        ;
        //bootstrap.bind(nodeServerConfig.getMessagePort()).sync();
        //log.info("node server successful! listening port: {}", nodeServerConfig.getMessagePort());
        return bootstrap;
    }

    public void Connect(DataCenterInfo info, int retry, int times) {
        Bootstrap bootstrap = init(info.getIp(), info.getMessagePort());
        bootstrap.connect()
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("连接数据中心成功");
                        Channel channel = ((ChannelFuture) future).channel();
                        List<AttributeEnum> attributeEnums = new ArrayList<>();
                        attributeEnums.add(AttributeEnum.CHANNEL_ATTR_DATACENTER);
                        channelManager.bindAttributes(info.getName(), channel, attributeEnums);
                        String ip = IpUtils.getIp();
                        channel.writeAndFlush(
                                Confirm.builder()
                                        .name(nodeServerConfig.getName())
                                        .nodeIpWithPort(ip + ":" + nodeServerConfig.getMessagePort())
                                        .build().encode()
                        );
                        deviceServerBootStrap.init();
                    } else if (retry == 0) {
                        log.error("无法连接到Data Center");
                    } else {
                        bootstrap.config().group().schedule(() -> {
                            Connect(info, retry - 1, times + 1);
                        }, 1 << times, TimeUnit.SECONDS);
                    }
                }).channel();
    }

    public void DisConnect(DataCenterInfo info) {
        channelManager.removeChannel(info.getName());
    }
}
