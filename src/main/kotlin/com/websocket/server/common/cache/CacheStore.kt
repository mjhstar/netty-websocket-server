package com.websocket.server.common.cache

import java.util.concurrent.ConcurrentHashMap

class CacheStore<K : Any, V>: ConcurrentHashMap<K,V>() {

    fun getValue(key: K): V? {
        return this[key]
    }

    fun getValues(): List<V> {
        return this.values.toList()
    }

    fun getKey(value: V): K?{
        var key: K? = null
        this.forEach { (k, v) ->
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
        this[key] = value
    }

    fun removeByKey(key: K) {
        this.remove(key)
    }

    fun removeByValue(value: V) {
        val key = getKey(value)
        key?.let{this.removeByKey(it)}
    }

    fun getValueOrDefault(key: K, defaultValue: V): V {
        return this.getOrDefault(key, defaultValue)
    }
}
