package domain.usecase

import domain.entity.CustomerMitra
import domain.entity.Hutang

// Input Boundary / Input Port
interface BuatHutangBaruUseCase {
    operator fun invoke(param: Param)

    // Request Model
    data class Param(
        val mitraId: Long,
        val customerMitraId: Long,
        val amount: Long,
        val desc: String
    )
}

// Interactor
class BuatHutangBaruUseCaseImpl(
    private val genericRepository: GenericRepository,
    private val presenter: Presenter
) : BuatHutangBaruUseCase {
    override fun invoke(param: BuatHutangBaruUseCase.Param) {
        try {
            val customerMitra =
                genericRepository.find<CustomerMitra>(CustomerMitra::class.java.canonicalName) { customerMitra ->
                    customerMitra.id == param.customerMitraId && customerMitra.mitraId == param.mitraId
                }
            requireNotNull(customerMitra)

            val newHutang = customerMitra.buatHutangBaru(param.amount, param.desc)
            val newId = genericRepository.add<Hutang>(Hutang::class.java.canonicalName, newHutang)

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

// Response Model
data class HutangCreatedResponseModel(
    val hutangId: Long,
    val jumlah: Long,
    val deskripsi: String
)