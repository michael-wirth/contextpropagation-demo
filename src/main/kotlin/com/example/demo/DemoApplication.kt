package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.core.publisher.Hooks

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	// set netty threads to 2 for testing
	System.setProperty("reactor.netty.ioWorkerCount","2")
	Hooks.enableAutomaticContextPropagation()
	runApplication<DemoApplication>(*args)
}
