package com.websocket.server.websocket.handler

import com.websocket.server.common.cache.CacheStore
import com.websocket.server.common.support.getPathVariable
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.FullHttpRequest
import org.springframework.stereotype.Component

@Component
@Sharable
class CustomHandler(
    private val channelCache: CacheStore<String, ChannelHandlerContext>
) : SimpleChannelInboundHandler<FullHttpRequest>() {
    override fun channelRead0(ctx: ChannelHandlerContext, request: FullHttpRequest) {
        processConnectionChannel(ctx, request)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        channelCache.removeByValue(ctx)
        cause.printStackTrace()
        ctx.close()
    }

    private fun processConnectionChannel(ctx: ChannelHandlerContext, request: FullHttpRequest) {
        val uri = request.uri()
        val pathVariable = uri.getPathVariable()
        channelCache.setValue(pathVariable, ctx)
        val modifiedRequest = request.copy().apply {
            this.uri = "/websocket"
        }
        ctx.fireChannelRead(modifiedRequest)
    }
}