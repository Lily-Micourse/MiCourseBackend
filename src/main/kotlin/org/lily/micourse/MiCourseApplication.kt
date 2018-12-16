package org.lily.micourse

import org.lily.micourse.config.FileStorageProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(value = [FileStorageProperties::class])
class MiCourseApplication

fun main(args: Array<String>) {
    runApplication<MiCourseApplication>(*args)
}

