package domain.entity

import java.util.Date

// Entity
class Hutang(
    val id: Long? = null,
    val deskripsi: String,
    val jumlahPinjaman: Long,
    val tanggalPinjam: Date = Date(),
    private val pembayaran: MutableList<Pembayaran> = mutableListOf()
) {
    init {
        require(jumlahPinjaman > 0)
    }

    @Throws(IllegalArgumentException::class)
    fun bayarHutang(jumlah: Long) {
        require(jumlah > 0)
        pembayaran.add(
            Pembayaran(jumlah, Date())
        )
    }

    fun lunasiHutang() {
        pembayaran.add(
            Pembayaran(jumlahPinjaman, Date())
        )
    }

    fun sisaHutang(): Long =
        jumlahPinjaman - pembayaran.map { it.jumlah }.sum()

    fun statusHutang(): StatusHutang =
        sisaHutang().let { sisa ->
            when {
                sisa < 0L -> throw IllegalStateException("sisa hutang tidak boleh minus")
                sisa == 0L -> StatusHutang.LUNAS
                sisa > 0L -> StatusHutang.BELUM_LUNAS
                else -> throw Exception()
            }
        }

    fun riwayatPembayaran(): List<Pembayaran> = pembayaran
}

data class Pembayaran(val jumlah: Long, val date: Date)

enum class StatusHutang { LUNAS, BELUM_LUNAS; }