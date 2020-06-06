package domain.usecase

import domain.entity.Loan
import domain.usecase.fakes.FakeInMemoryLoanRepository
import domain.usecase.fakes.SpyValuePresenter
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class GetLenderAndBorrowerLoanEntriesUseCaseImplTest {

    private lateinit var getLenderAndBorrowerLoanEntries: GetLenderAndBorrowerLoanEntriesUseCaseImpl
    private lateinit var loanEntryPresenter: SpyValuePresenter<List<LoanEntry>>
    private lateinit var loanRepository: FakeInMemoryLoanRepository

    @Before
    fun setUp() {
        loanEntryPresenter = SpyValuePresenter()
        loanRepository = FakeInMemoryLoanRepository()

        val lazyGetLenderAndBorrowerLoanEntries =
            lazy { GetLenderAndBorrowerLoanEntriesUseCaseImpl(loanEntryPresenter, loanRepository) }

        getLenderAndBorrowerLoanEntries = lazyGetLenderAndBorrowerLoanEntries.value
    }

    @Test
    fun getBorrowerLoanEntries_ShouldGetLoanEntries() {
        val fakeLoan = Loan(desc = "sample-loan", amount = 100_000, borrowerId = 25, lenderId = 30)
        loanRepository.add(fakeLoan)

        val result = getLoanEntriesFrom(25, 30)
        assertThat(result.size, not(equalTo(0)))
        assertThat(result.first().desc, equalTo(fakeLoan.desc))
        assertThat(result.first().amount, equalTo(fakeLoan.amount))
    }

    @Test
    fun getLenderAndBorrowerLoanEntries_ShouldFailWhenBothIdNotMatching() {
        createAndStoreLoan("sample-loan-1", 100_000, 25, 30)
        createAndStoreLoan("sample-loan-2", 200_000, 40, 30)
        createAndStoreLoan("sample-loan-3", 200_000, 40, 31)
        createAndStoreLoan("sample-loan-4", 500_000, 25, 30)

        assertThat(getLoanEntriesFrom(25, 90).size, equalTo(0))
        assertThat(getLoanEntriesFrom(40, 30).size, equalTo(1))
        assertThat(getLoanEntriesFrom(25, 30).size, equalTo(2))
    }

    private fun createAndStoreLoan(
        desc: String,
        amount: Long,
        borrowerId: Long,
        lenderId: Long
    ) {
        loanRepository.add(Loan(desc = desc, amount = amount, borrowerId = borrowerId, lenderId = lenderId))
    }

    private fun getLoanEntriesFrom(borrowerId: Long, lenderId: Long): List<LoanEntry> {
        getLenderAndBorrowerLoanEntries(borrowerId, lenderId)
        return loanEntryPresenter.successResult!!
    }
}

