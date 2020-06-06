package domain.entity

import java.util.Date

class Loan(
    val id: Long? = null,
    val desc: String,
    val amount: Long,
    val borrowerId: Long,
    val lenderId: Long,
    val timeStamp: Date = Date()
) {
    init {
        require(amount > 0)
        require(desc.isNotBlank())
        require(borrowerId > 0)
    }
}