package domain.entity

import java.util.Date

class Hutang(
    val id: Long,
    val idCatatanHutang: Long,
    val deskripsi: String,
    val jumlahPinjaman: Long,
    val tanggalPinjam: Date
) {
    private val pembayaran: MutableList<Pembayaran> = mutableListOf()

    fun lunasiHutang() {
        pembayaran.add(
            Pembayaran(jumlahPinjaman, Date())
        )
    }

    fun bayarHutang(jumlah: Long) {
        require(jumlah > 0)
        pembayaran.add(
            Pembayaran(jumlah, Date())
        )
    }

    fun sisaHutang(): Long =
        jumlahPinjaman + pembayaran.map { it.jumlah }.sum()

    fun statusHutang(): String =
        sisaHutang().let { sisa ->
            when {
                sisa < 0L -> throw IllegalStateException("sisa hutang tidak boleh minus")
                sisa == 0L -> "lunas"
                sisa > 0L -> "belum lunas"
                else -> ""
            }
        }
}

data class Pembayaran(val jumlah: Long, val date: Date)