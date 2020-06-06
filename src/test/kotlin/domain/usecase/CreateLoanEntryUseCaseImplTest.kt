package domain.usecase

import domain.usecase.CreateLoanEntryUseCaseImpl
import domain.usecase.LoanInformation
import domain.usecase.fakes.FakeInMemoryLoanRepository
import domain.usecase.fakes.SpySimplePresenter
import org.hamcrest.CoreMatchers.hasItems
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class CreateLoanEntryUseCaseImplTest {
    private lateinit var createLoanEntryUseCase: CreateLoanEntryUseCaseImpl
    private lateinit var fakeLoanRepository: FakeInMemoryLoanRepository
    private lateinit var spySimplePresenter: SpySimplePresenter

    @Before
    fun setUp() {
        fakeLoanRepository = FakeInMemoryLoanRepository()
        spySimplePresenter = SpySimplePresenter()
        createLoanEntryUseCase =
            CreateLoanEntryUseCaseImpl(fakeLoanRepository, spySimplePresenter)
    }

    private val loanInformation = LoanInformation(
        1, 2, 50000L, "pinjaman"
    )

    @Test
    fun createLoanShouldPresentSuccess() {
        createLoanEntryUseCase.invoke(loanInformation)

        assertThat(spySimplePresenter.state, hasItems("success", "complete"))
    }

    @Test
    fun createLoanShouldPresentErrorOnFailure() {
        fakeLoanRepository.alwaysFail = true
        createLoanEntryUseCase.invoke(loanInformation)

        assertThat(spySimplePresenter.state, hasItems("error", "complete"))
    }
}

