package org.lily.micourse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class MiCourseApplication

fun main(args: Array<String>) {
    runApplication<MiCourseApplication>(*args)
}

