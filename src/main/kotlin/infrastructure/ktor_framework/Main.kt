package infrastructure.ktor_framework

import domain.entity.CustomerMitra
import domain.entity.Mitra
import domain.usecase.BuatHutangBaruUseCase
import domain.usecase.BuatHutangBaruUseCaseImpl
import infrastructure.adapters.JsonPresenter
import infrastructure.gateway.InMemoryMitraRepository
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, port = 8080) {
        install(DefaultHeaders)
        seed()

        routing {
            get("/") {
                call.respondText("You are connected", ContentType.Text.Plain)
            }

            get("/create-debts") {
                val viewModel = JsonPresenter.ViewModel()
                val presenter = JsonPresenter(viewModel)
                val useCase = BuatHutangBaruUseCaseImpl(InMemoryMitraRepository, presenter)

                useCase.invoke(BuatHutangBaruUseCase.Param(1,1,100000, "coba-coba"))

                call.respondText(viewModel.value ?: viewModel.error.toString())
            }
        }
    }

    server.start(wait = true)
}

private fun seed() {
    InMemoryMitraRepository.add(
        Mitra::class.java.canonicalName,
        Mitra(
            1, "denny", emptyList()
        )
    )

    InMemoryMitraRepository.add(
        CustomerMitra::class.java.canonicalName,
        CustomerMitra(
            1,1,"bangkit", "0812-1234-1234"
        )
    )
}