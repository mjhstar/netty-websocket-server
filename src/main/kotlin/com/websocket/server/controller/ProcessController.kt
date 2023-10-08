package com.websocket.server.controller

import com.websocket.server.service.ProcessService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProcessController(
    private val processService: ProcessService
) {
    @PostMapping("/process/{pathVariable}")
    fun processTest(
        @PathVariable pathVariable: String
    ){
        processService.processTest(pathVariable)
    }

    @PostMapping("/remove/{pathVariable}")
    fun removeChannel(
        @PathVariable pathVariable: String
    ){
        processService.removeChannel(pathVariable)
    }
}