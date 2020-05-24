package domain.usecase

import domain.entity.Debitur
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import java.lang.IllegalStateException

class TambahDebiturUseCaseInteractorTest {

    private lateinit var tambahDebiturUseCase: TambahDebiturUseCaseInteractor
    private lateinit var debiturStorage: SpyDebiturStorage
    private lateinit var presenter: SpyPresenter

    @Before
    fun setUp() {
        debiturStorage = SpyDebiturStorage()
        presenter = SpyPresenter()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun tambahDebiturShouldStoreData() {
        val debitur = Debitur(name = "Denny", address = "Jl. Raya no.1A", phoneNumber = "0812356789")

        tambahDebiturUseCase = TambahDebiturUseCaseInteractor(debiturStorage, presenter)
        tambahDebiturUseCase.invoke()

        assertThat(debiturStorage.data.size, equalTo(1))
        assertThat(debiturStorage.data.first(), equalTo(debitur))
    }

    @Test
    fun tambahDebiturShouldPresentSuccess() {
        val debitur = Debitur(name = "Denny", address = "Jl. Raya no.1A", phoneNumber = "0812356789")

        tambahDebiturUseCase = TambahDebiturUseCaseInteractor(debiturStorage, presenter)
        tambahDebiturUseCase.invoke()

        assertThat(presenter.successMessage, notNullValue())
    }

    @Test
    fun tambahDebiturWithNonExistingIdShouldPresentError() {
        val debitur = Debitur(id = 100L, name = "Denny", address = "Jl. Raya no.1A", phoneNumber = "0812356789")

        tambahDebiturUseCase = TambahDebiturUseCaseInteractor(debiturStorage, presenter)
        tambahDebiturUseCase.invoke()

        assertThat(presenter.error, notNullValue())
    }

    private class SpyDebiturStorage : DebiturStorage {
        val data: MutableList<Debitur> = mutableListOf()

        override fun store(debitur: Debitur) {
            if (debitur.id != null && data.find { it.id == debitur.id } == null) throw IllegalStateException()
            data += debitur
        }
    }

    private class SpyPresenter : TambahDebiturUseCase.Presenter {
        var error: Exception? = null
        var successMessage: String? = null

        override fun presentError(e: Exception) {
            error = e
        }

        override fun presentSuccess(message: String) {
            successMessage = message
        }
    }
}
