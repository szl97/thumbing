package com.loserclub.pushdata.nodeserver.client;

import com.loserclub.pushdata.common.Infos.DataCenterInfo;
import com.loserclub.pushdata.common.constants.AttributeEnum;
import com.loserclub.pushdata.common.constants.OperationEnum;
import com.loserclub.pushdata.nodeserver.channel.DeviceDataChannelManager;
import com.loserclub.pushdata.nodeserver.channel.SyncClientChannelManager;
import com.loserclub.pushdata.nodeserver.config.NodeServerConfig;
import com.loserclub.pushdata.nodeserver.inbound.NodeToCenterInBoundSyncHandler;
import com.loserclub.pushdata.nodeserver.messages.ConnectSet;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 与设备客户端建立连接后，作为消息中心的客户端，将建立连接的消息发送给data-center
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class SyncConnectClientBootStrap {

    @Autowired
    private NodeServerConfig nodeServerConfig;

    private NioEventLoopGroup group = new NioEventLoopGroup();

    @Autowired
    private SyncClientChannelManager channelManager;

    @Autowired
    private NodeToCenterInBoundSyncHandler nodeToCenterInBoundSyncHandler;

    @Autowired
    private DeviceDataChannelManager deviceDataChannelManager;

    public Bootstrap init(String ip, int port)  {
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

                                 //处理Node 心跳事件、node server与客户端之间连接的建立和删除事件
                                 pipeline.addLast("handler", nodeToCenterInBoundSyncHandler);
                             }
                         }
                ).option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true);;
        //bootstrap.bind(nodeServerConfig.getPort()).sync();
        //log.info("node server successful! listening port: {}", nodeServerConfig.getPort());
        return bootstrap;
    }

    public void Connect(DataCenterInfo info, int retry, int times){
        Bootstrap bootstrap = init(info.getIp(), info.getPort());
        bootstrap.connect()
                .addListener(future -> {
                    if(future.isSuccess()){
                        log.info("连接数据中心成功");
                        Channel channel = ((ChannelFuture)future).channel();
                        List<AttributeEnum> attributeEnums = new ArrayList<>();
                        attributeEnums.add(AttributeEnum.CHANNEL_ATTR_DATACENTER);
                        channelManager.bindAttributes(info.getName(), channel, attributeEnums);
                        List<Long> devices = deviceDataChannelManager.getAllDevices();
                        if(devices.size() > 0){
                            channel.writeAndFlush(
                                    ConnectSet.builder()
                                    .name(nodeServerConfig.getName())
                                    .operation(OperationEnum.ADD)
                                    .deviceIds(devices)
                                    .build()
                                    .encode()
                            );
                        }
                    }
                    else if(retry == 0){
                        //无法连接到Data Center
                        log.error("无法连接到Data Center");
                    }
                    else{
                        bootstrap.config().group().schedule(()->{Connect(info, retry-1, times+1);}, 1<<times, TimeUnit.SECONDS);
                    }
                }).channel();
    }

    public void DisConnect(DataCenterInfo info){
        channelManager.removeChannel(info.getName());
    }
}
