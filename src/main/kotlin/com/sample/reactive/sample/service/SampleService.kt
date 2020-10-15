package com.sample.reactive.sample.service

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration


@Service
class SampleService {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(SampleService::class.java)
    }


    fun generateRandomData() = Flux.range(1,100).flatMap { Mono.just(SampleData(it, "Sample $it")) }

    fun generateRandomDataWithDelay()
        = generateRandomData().delayElements(Duration.ofSeconds(1))

    fun recoveryDataOrSaveFile() : Flux<List<SampleData>> {
        val flux = generateRandomDataWithDelay()
        val fluxFile = Flux.from(flux)
        return flux.buffer().timeout(Duration.ofSeconds(4)).doOnError {
            LOGGER.info("error")
            fluxFile.subscribe(fallback())
        }
      }

    fun fallback() = object : Subscriber<SampleData>  {
        override fun onSubscribe(subscription: Subscription) {
            LOGGER.info("subscribe")
            subscription.request(Long.MAX_VALUE)
        }

        override fun onNext(sampleData: SampleData) {
            LOGGER.info("receive data $sampleData")
        }

        override fun onError(t: Throwable) {
            LOGGER.error("deu erro ", t)
        }

        override fun onComplete() {
            LOGGER.info("complete")
        }
    }
}


data class SampleData(val id: Int, val name: String)
