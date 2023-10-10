package com.example.demo

import io.micrometer.observation.ObservationRegistry
import io.micrometer.tracing.Tracer
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.observability.micrometer.Micrometer
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
class MyController(private val registry: ObservationRegistry, private val tracer: Tracer) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/context")
    fun context() =
        Mono.fromCallable {
            logger.info(registry.currentObservation?.context?.toString())
            val context = checkNotNull(tracer.currentTraceContext().context())
            logger.info(context.toString())
            context.toString()
        }

    @GetMapping("/ping")
    fun ping() =
        Mono.fromCallable {
            logger.info("ping")
            "pong"
        }.subscribeOn(Schedulers.boundedElastic())
            .tag("name", "ping")
            .tap(Micrometer.observation(registry))
}