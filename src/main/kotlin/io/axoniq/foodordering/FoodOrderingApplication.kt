package io.axoniq.foodordering

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class FoodOrderingApplication


fun main(args: Array<String>) {
    SpringApplication.run(FoodOrderingApplication::class.java, *args)
}
