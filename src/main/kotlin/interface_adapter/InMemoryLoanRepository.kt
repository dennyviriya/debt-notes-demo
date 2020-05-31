package interface_adapter

import domain.entity.Loan
import domain.usecase.port.LoanRepository

class InMemoryLoanRepository: LoanRepository, GenericInMemoryRepository<Loan>() {
    override fun getAllLoans(): List<Loan> {
        return map.values.toList()
    }

    override fun getLoanFrom(borrowerId: Long): List<Loan> {
        return map.values.toList().filter { it.borrowerId == borrowerId }
    }
}