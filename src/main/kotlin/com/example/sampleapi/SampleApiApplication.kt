package com.example.sampleapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans

@SpringBootApplication
@ComponentScan("com.example.sampleapi")
class SampleApiApplication

fun main(args: Array<String>) {
  runApplication<SampleApiApplication>(*args)
}
