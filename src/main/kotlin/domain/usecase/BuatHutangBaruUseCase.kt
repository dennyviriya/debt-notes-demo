package domain.usecase

import domain.entity.CustomerMitra

// Input Boundary / Input Port
interface BuatHutangBaruUseCase {
    operator fun invoke(param: Param)

    // Request Model, this can also be simple data structure like Map (or Hash in ruby)
    data class Param(
        val mitraId: Long,
        val customerMitraId: Long,
        val amount: Long,
        val desc: String
    )
}

// Interactor or UseCase Implementation
class BuatHutangBaruUseCaseImpl(
    // a proper approach would be using Unit of Work Pattern since I'm using Domain Driven Design approach
    private val genericRepository: GenericRepository,
    private val presenter: Presenter
) : BuatHutangBaruUseCase {
    override fun invoke(param: BuatHutangBaruUseCase.Param) {
        try {
            val customerMitra =
                genericRepository.find(CustomerMitra::class) { customerMitra ->
                    customerMitra.id == param.customerMitraId && customerMitra.mitraId == param.mitraId
                }
            requireNotNull(customerMitra)

            val newHutang = customerMitra.buatHutangBaru(param.amount, param.desc)
            val newId = genericRepository.add(newHutang)

            presenter.onHutangCreated(
                HutangCreatedResponseModel(newId, newHutang.jumlahPinjaman, newHutang.deskripsi)
            )
        } catch (e: Exception) {
            presenter.onFailCreateHutang(e)
        }
    }
}

// Output Boundary / Output Port
interface Presenter {
    fun onHutangCreated(response: HutangCreatedResponseModel)
    fun onFailCreateHutang(exception: Exception)
}

// Response Model, this can also be simple data structure like Map (or Hash in ruby)
data class HutangCreatedResponseModel(
    val hutangId: Long,
    val jumlah: Long,
    val deskripsi: String
)