package org.lily.micourse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MicourseApplication

fun main(args: Array<String>) {
    runApplication<MicourseApplication>(*args)
}
