package infrastructure.adapters

import domain.usecase.BuatHutangBaruUseCase
import domain.usecase.BuatHutangBaruUseCase.Param

class DebtController(
    private val buatHutangBaruUseCase: BuatHutangBaruUseCase
) {
    fun createDebt(mitraId: Long, amount: Long, customerMitraId: Long, desc: String) {
        val param = Param(mitraId, customerMitraId, amount, desc)
        buatHutangBaruUseCase.invoke(param)
    }

    interface Factory {
        fun create(): DebtController
    }
}