package domain.entity

sealed class Transaksi(
    val id: Long? = null,
    val amount: Long,
    val description: String,
    val kreditur: Long,
    val debitur: Long
) {
    class Bayar(id: Long? = null, amount: Long, description: String, kreditur: Long, debitur: Long) :
        Transaksi(id, amount, description, kreditur, debitur)

    class Pinjam(id: Long? = null, amount: Long, description: String, kreditur: Long, debitur: Long) :
        Transaksi(id, amount, description, kreditur, debitur)
}