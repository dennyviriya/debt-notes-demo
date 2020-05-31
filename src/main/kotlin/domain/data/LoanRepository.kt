package domain.data

import domain.entity.Loan

interface LoanRepository : GenericRepository<Loan> {
    fun getAllLoans(): List<Loan>
    fun getLoanFrom(borrowerId: Long): List<Loan>
}

class InMemoryLoanRepository: LoanRepository, InMemoryRepository<Loan>() {
    override fun getAllLoans(): List<Loan> {
        return map.values.toList()
    }

    override fun getLoanFrom(borrowerId: Long): List<Loan> {
        return map.values.toList().filter { it.borrowerId == borrowerId }
    }
}