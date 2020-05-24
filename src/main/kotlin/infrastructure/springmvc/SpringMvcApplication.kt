package infrastructure.springmvc

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class SpringMvcApplication {
    companion object {
        @JvmStatic
        fun main(vararg args: String) {
            SpringApplication.run(SpringMvcApplication::class.java, *args)
        }
    }
}