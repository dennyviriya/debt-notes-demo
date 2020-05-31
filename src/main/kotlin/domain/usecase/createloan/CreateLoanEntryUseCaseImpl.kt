package domain.usecase.createloan

import domain.data.LoanRepository
import domain.entity.Loan
import domain.usecase.port.SimplePresenter
import java.util.Date

class CreateLoanEntryUseCaseImpl(
    private val repository: LoanRepository,
    private val presenter: SimplePresenter
) : CreateLoanEntryUseCase {
    private val now = Date()

    override fun invoke(loanInformation: LoanInformation) {
        try {
            makeLoan(loanInformation)
            presenter.onSuccess()
        } catch (e: Exception) {
            presenter.onError(e)
        } finally {
            presenter.onComplete()
        }
    }

    private fun makeLoan(loanInformation: LoanInformation) {
        val (borrowerId, lenderId, amount, description) = loanInformation

        val loan = Loan(
            desc = description,
            amount = amount,
            borrowerId = borrowerId,
            lenderId = lenderId,
            timeStamp = now
        )
        repository.add(loan)
    }
}

