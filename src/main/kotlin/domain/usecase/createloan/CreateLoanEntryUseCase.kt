package domain.usecase.createloan

interface CreateLoanEntryUseCase {
    operator fun invoke(loanInformation: LoanInformation)
}