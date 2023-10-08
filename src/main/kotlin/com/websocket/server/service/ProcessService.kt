package com.websocket.server.service

import com.websocket.server.common.cache.CacheStore
import io.netty.channel.ChannelHandlerContext
import org.springframework.stereotype.Service

@Service
class ProcessService(
    private val channelCache: CacheStore<String, ChannelHandlerContext>
) {
    fun processTest(pathVariable: String){
        val channel = channelCache.getValue(pathVariable)
        TODO("channel에 해야하는 액션 진행")
    }

    fun removeChannel(pathVariable: String){
        channelCache.getValue(pathVariable)?.let{
            channelCache.removeByKey(pathVariable)
            it.close()
        }
    }
}