package com.sample.reactive.sample.controller

import com.sample.reactive.sample.service.SampleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sample")
class SampleController(val sampleService: SampleService) {

    @GetMapping
    fun get() = sampleService.generateRandomData()

    @GetMapping("/delay")
    fun getWithDelay() = sampleService.recoveryDataOrSaveFile()

}
