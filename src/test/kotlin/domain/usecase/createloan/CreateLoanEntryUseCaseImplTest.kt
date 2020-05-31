package domain.usecase.createloan

import org.hamcrest.CoreMatchers.hasItems
import org.junit.After
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
        createLoanEntryUseCase = CreateLoanEntryUseCaseImpl(fakeLoanRepository, spySimplePresenter)
    }

    @After
    fun tearDown() {
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

