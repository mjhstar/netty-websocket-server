package com.websocket.server.websocket.handler

import com.websocket.server.common.cache.CacheStore
import com.websocket.server.websocket.WebSocketProcessService
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.websocketx.WebSocketFrame
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler
import org.springframework.stereotype.Component

@Component
@Sharable
class WebSocketServerHandler(
    private val channelCache: CacheStore<String, ChannelHandlerContext>,
    private val webSocketProcessService: WebSocketProcessService
) : SimpleChannelInboundHandler<WebSocketFrame>() {

    override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any?) {
        //HandShake 완료 시, streaming 시작
        if (evt is WebSocketServerProtocolHandler.HandshakeComplete) {
            webSocketProcessService.process(ctx)
            TODO("웹 소켓 연결 완료, 연결된 웹 소켓에 해야하는 작업을 하는 서비스 호출")
        }
    }

    override fun channelActive(ctx: ChannelHandlerContext) {
        super.channelActive(ctx)
    }

    override fun channelRead0(ctx: ChannelHandlerContext, msg: WebSocketFrame) {
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable?) {
        channelCache.removeByValue(ctx)
        super.exceptionCaught(ctx, cause)
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        channelCache.removeByValue(ctx)
        super.channelInactive(ctx)
    }
}

