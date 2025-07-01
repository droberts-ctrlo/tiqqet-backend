package blog.davetheitguy.tiqqet.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TiqqetBackendApplication

fun main(args: Array<String>) {
    runApplication<TiqqetBackendApplication>(*args)
}
