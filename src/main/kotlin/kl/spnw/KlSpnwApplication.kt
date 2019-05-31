package kl.spnw

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class KlSpnwApplication

fun main(args: Array<String>) {
	runApplication<KlSpnwApplication>(*args)
}
