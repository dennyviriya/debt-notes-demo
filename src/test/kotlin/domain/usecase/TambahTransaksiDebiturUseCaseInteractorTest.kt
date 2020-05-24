package domain.usecase

import domain.data.TransaksiStorage
import domain.entity.Transaksi
import domain.usecase.TambahTransaksiDebiturUseCase.JenisTransaksi
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class TambahTransaksiDebiturUseCaseInteractorTest {

    private lateinit var tambahTransaksiDebiturUseCase: TambahTransaksiDebiturUseCaseInteractor
    private lateinit var spyStorage: SpyStorage
    private lateinit var spyPresenter: SpyPresenter

    @Before
    fun setUp() {
        spyStorage = SpyStorage()
        spyPresenter = SpyPresenter()
    }

    @Test
    fun storeTransaksi() {
        val param = TambahTransaksiDebiturUseCase.TransactionData(
            JenisTransaksi.PINJAM, 50000L, "pinjam biasa", 1L, 2L
        )

        tambahTransaksiDebiturUseCase = TambahTransaksiDebiturUseCaseInteractor(spyStorage, spyPresenter, param)
        tambahTransaksiDebiturUseCase.invoke()

        with(spyStorage.lastTransaksi!!) {
            assertThat(amount, equalTo(param.amount))
            assertThat(description, equalTo(param.description))
            assertThat(kreditur, equalTo(param.krediturId))
            assertThat(debitur, equalTo(param.debiturId))
        }
        assertThat(spyPresenter.successMessage, notNullValue())
    }

    private class SpyStorage : TransaksiStorage {
        var lastTransaksi: Transaksi? = null

        override fun store(transaksi: Transaksi) {
            lastTransaksi = transaksi
        }
    }

    private class SpyPresenter : TambahTransaksiDebiturUseCase.Presenter {
        var successMessage: String? = null
        var error: Exception? = null

        override fun presentSuccess(message: String) {
            successMessage = message
        }

        override fun presentError(e: Exception) {
            error = e
        }
    }
}