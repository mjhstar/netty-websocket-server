package com.websocket.server.websocket

import io.netty.channel.ChannelHandlerContext
import org.springframework.stereotype.Service

@Service
class WebSocketProcessService(
) {
    fun process(ctx: ChannelHandlerContext) {
        TODO("첫 채널 연결에 해야하는 action")
    }
}