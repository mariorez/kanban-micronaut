package org.seariver

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("org.seariver")
        .start()
}
