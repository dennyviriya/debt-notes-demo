package domain.usecase

import domain.entity.Loan
import domain.usecase.port.LoanRepository
import domain.usecase.port.SimplePresenter
import java.util.Date

interface CreateLoanEntryUseCase {
    operator fun invoke(loanInformation: LoanInformation)
}

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

    @Throws(Exception::class)
    private fun makeLoan(loanInformation: LoanInformation) {
        val (borrowerId, lenderId, amount, description) = loanInformation

        val loanEntry = Loan(
            desc = description,
            amount = amount,
            borrowerId = borrowerId,
            lenderId = lenderId,
            timeStamp = now
        )
        repository.add(loanEntry)
    }
}

data class LoanInformation(
    val borrowerId: Long,
    val lenderId: Long,
    val amount: Long,
    val description: String
)