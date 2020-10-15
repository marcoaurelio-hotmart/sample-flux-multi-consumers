package com.sample.reactive.sample

import com.sample.reactive.sample.controller.SampleController
import com.sample.reactive.sample.service.SampleData
import com.sample.reactive.sample.service.SampleService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@WebFluxTest(controllers = [SampleController::class])
@Import(SampleService::class)
class SampleTests {

    @Autowired
    lateinit var webClient: WebTestClient

    @Test
    fun sampleTest() {
        webClient.get()
                .uri("/sample")
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBodyList(SampleData::class.java)
                .hasSize(100)
    }

    @Test
    fun sampleDelayTest() {
        webClient.get()
                .uri("/sample/delay")
                .exchange()
                .expectStatus().is5xxServerError


    }

}
