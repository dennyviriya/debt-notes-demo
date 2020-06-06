package domain.usecase.port

import domain.entity.Loan

interface LoanRepository : GenericRepository<Loan> {
    fun getAllLoans(): List<Loan>
    fun getLoanFrom(borrowerId: Long, lenderId: Long): List<Loan>
}

