package com.websocket.server.common.cache

import java.util.concurrent.ConcurrentHashMap

class CacheStore<K : Any, V> {
    private val cache = ConcurrentHashMap<K, V>()

    fun getValue(key: K): V? {
        return cache[key]
    }

    fun getValues(): List<V> {
        return cache.values.toList()
    }

    fun getKey(value: V): K?{
        var key: K? = null
        cache.forEach { (k, v) ->
            if(v == value){
                key = k
                return@forEach
            }
        }
        return key
    }

    fun setValue(key: K, value: V) {
        if(getValues().contains(value)){
            return
        }
        cache[key] = value
    }

    fun removeByKey(key: K) {
        cache.remove(key)
    }

    fun removeByValue(value: V) {
        val key = getKey(value)
        key?.let{this.removeByKey(it)}
    }

    fun getValueOrDefault(key: K, defaultValue: V): V {
        return cache.getOrDefault(key, defaultValue)
    }

}