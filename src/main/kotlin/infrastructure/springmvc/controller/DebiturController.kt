package infrastructure.springmvc.controller

import infrastructure.springmvc.model.Debitur
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class DebiturController {
    private val mId = AtomicLong()

    @GetMapping("/debitur")
    fun getDebiturs(
        @RequestParam(value = "name", defaultValue = "") name: String
    ): Debitur {
        return Debitur(id = mId.incrementAndGet(), name = name)
    }
}
