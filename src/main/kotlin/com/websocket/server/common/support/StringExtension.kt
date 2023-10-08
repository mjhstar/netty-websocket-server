package com.websocket.server.common.support

fun String.getPathVariable(): String{
    return this.split("/")[2]
}