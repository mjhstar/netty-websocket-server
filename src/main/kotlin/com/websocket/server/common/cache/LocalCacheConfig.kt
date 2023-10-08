package com.websocket.server.common.cache

import io.netty.channel.ChannelHandlerContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LocalCacheConfig {
    @Bean
    fun channelCache(): CacheStore<String, ChannelHandlerContext> {
        return CacheStore()
    }

}