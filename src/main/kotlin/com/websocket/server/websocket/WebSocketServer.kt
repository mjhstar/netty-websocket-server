package com.websocket.server.websocket

import com.websocket.server.websocket.handler.CustomHandler
import com.websocket.server.websocket.handler.WebSocketServerHandler
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpServerCodec
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import java.net.InetSocketAddress
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class WebSocketServer(
    private val webSocketServerHandler: WebSocketServerHandler,
    private val customHandler: CustomHandler
) {
    private lateinit var bossGroup: NioEventLoopGroup
    private lateinit var workerGroup: NioEventLoopGroup

    @PostConstruct
    fun startWebSocketServer() {
        bossGroup = NioEventLoopGroup()
        workerGroup = NioEventLoopGroup()

        val serverBootstrap = ServerBootstrap()
        serverBootstrap.group(bossGroup, workerGroup).localAddress(InetSocketAddress(9999))
            .channel(NioServerSocketChannel::class.java)
            .option(ChannelOption.SO_BACKLOG, 128)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .childHandler(object : ChannelInitializer<SocketChannel>() {
                override fun initChannel(ch: SocketChannel) {
                    ch.pipeline().addLast(HttpServerCodec())
                    ch.pipeline().addLast(HttpObjectAggregator(65536))
                    ch.pipeline().addLast(customHandler)
                    ch.pipeline().addLast(WebSocketServerProtocolHandler("/websocket"))
                    ch.pipeline().addLast(webSocketServerHandler)
                }
            })
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val channel = serverBootstrap.bind().sync().channel()
                channel.closeFuture().sync()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @PreDestroy
    fun stopWebSocketServer() {
        try {
            bossGroup.shutdownGracefully().sync()
            workerGroup.shutdownGracefully().sync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
