package domain.usecase.createloan

import domain.entity.Loan
import infrastructure.adapters.InMemoryLoanRepository

class FakeInMemoryLoanRepository : InMemoryLoanRepository() {
    var alwaysFail = false

    override fun add(value: Loan) {
        if (alwaysFail) throw Exception("STORAGE ERROR")
        super.add(value)
    }
}