package domain.usecase.createloan

data class LoanInformation(
    val borrowerId: Long,
    val lenderId: Long,
    val amount: Long,
    val description: String
)