package domain.usecase

import domain.usecase.port.LoanRepository
import domain.usecase.port.ValuePresenter

interface GetLenderAndBorrowerLoanEntriesUseCase {
    operator fun invoke(borrowerId: Long, lenderId: Long)
}

class GetLenderAndBorrowerLoanEntriesUseCaseImpl(
    private val presenter: ValuePresenter<List<LoanEntry>>,
    private val loanRepository: LoanRepository
) : GetLenderAndBorrowerLoanEntriesUseCase {
    override operator fun invoke(borrowerId: Long, lenderId: Long) {
        val result = loanRepository.getLoanFrom(borrowerId, lenderId)
        val loanEntries = result.map {
            LoanEntry(it.amount, it.desc)
        }
        presenter.onCompleted(Result.success(loanEntries))
    }
}

data class LoanEntry(
    val amount: Long,
    val desc: String
)

