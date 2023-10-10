//package com.example.demo
//
//import io.micrometer.context.ContextSnapshotFactory
//import io.opentelemetry.context.Context
//import org.slf4j.LoggerFactory
//import org.slf4j.MDC
//import org.springframework.core.Ordered
//import org.springframework.core.annotation.Order
//import org.springframework.stereotype.Component
//import org.springframework.util.ReflectionUtils
//import org.springframework.web.server.ServerWebExchange
//import org.springframework.web.server.WebFilter
//import org.springframework.web.server.WebFilterChain
//import reactor.core.publisher.Mono
//
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//class MyFilter : WebFilter {
//    val log = LoggerFactory.getLogger(javaClass)
//    private val factory = ContextSnapshotFactory.builder().clearMissing(true).build()
//    private val contextStore: ThreadLocal<Context>
//
//    init {
//        val clazz = Class.forName("io.opentelemetry.context.ThreadLocalContextStorage")
//        val field = checkNotNull(ReflectionUtils.findField(clazz, "THREAD_LOCAL_STORAGE"))
//        ReflectionUtils.makeAccessible(field)
//        contextStore = ReflectionUtils.getField(field, null) as ThreadLocal<Context>
//    }
//
//    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
//        log.info("filter")
//        log.info(contextStore.get()?.toString())
//        return chain.filter(exchange)
//            .doOnTerminate {
//                log.info("finally")
//                log.info(contextStore.get()?.toString())
//                contextStore.remove()
//                MDC.clear()
//                log.info(contextStore.get()?.toString())
//            }
//    }
//}