package com.example.demo

import io.micrometer.observation.Observation.Context
import io.micrometer.tracing.CurrentTraceContext
import io.micrometer.tracing.Tracer
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class LogFilter(private val tracer: Tracer) : WebFilter {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        logger.info("enter {}", tracer.currentTraceContext().context())

        return chain.filter(exchange)
            .doOnTerminate {
                logger.info("exit {}", tracer.currentTraceContext().context())
                println()
            }
    }
}