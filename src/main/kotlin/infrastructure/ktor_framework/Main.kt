package infrastructure.ktor_framework

import domain.entity.CustomerMitra
import domain.entity.Mitra
import domain.usecase.BuatHutangBaruUseCase.Param
import domain.usecase.BuatHutangBaruUseCaseImpl
import infrastructure.adapters.JsonPresenter
import infrastructure.adapters.JsonPresenter.ViewModel
import infrastructure.gateway.InMemoryMitraRepository
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveParameters
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

// Just run this, it will be available at localhost:8080
fun main(args: Array<String>) {
    val server = embeddedServer(Netty, port = 8080) {
        install(DefaultHeaders)
        seed()

        routing {
            get("/") {
                call.respondText("You are connected", ContentType.Text.Plain)
            }

            // this could be your Controller
            // in Ktor framework, there is no explicit application structure so you can structure it however you like
            // the job of controller is formatting the data from the adapters and frameworks layer to the data structure most convenient for usecase
            post("/debt") {
                val param = call.receiveParameters()

                var useCaseParam: Param? = null

                // You can also verify this in entity, but thats another thing to research.
                // IMO, if you verify bad input early, you can save more performance
                try {
                    val mitraId = param["mitra-id"]!!.toLong()
                    val customerMitraId = param["customer-mitra-id"]!!.toLong()
                    val amount = param["amount"]!!.toLong()
                    val description = param["desc"].orEmpty()

                    useCaseParam = Param(
                        mitraId = mitraId,
                        customerMitraId = customerMitraId,
                        amount = amount,
                        desc = description
                    )
                } catch (e: NullPointerException) {
                    call.respondText(
                        text = "There is missing required parameter",
                        status = HttpStatusCode.UnprocessableEntity
                    )
                } catch (e: NumberFormatException) {
                    call.respondText(
                        text = "there are 1 or more invalid number in the parameter",
                        status = HttpStatusCode.UnprocessableEntity
                    )
                } finally {
                    require(useCaseParam != null)
                }

                // This is how to do manual Dependency Injection
                val viewModel: ViewModel = ViewModel()
                val presenter: JsonPresenter = JsonPresenter(viewModel)
                val useCase: BuatHutangBaruUseCaseImpl = BuatHutangBaruUseCaseImpl(InMemoryMitraRepository, presenter)

                useCase.invoke(useCaseParam)
                call.respondText(viewModel.toString())
            }
        }
    }

    server.start(wait = true)
}

private fun seed() {
    InMemoryMitraRepository.add(
        Mitra(
            1, "denny", emptyList()
        )
    )

    InMemoryMitraRepository.add(
        CustomerMitra(
            1, 1, "bangkit", "0812-1234-1234"
        )
    )
}