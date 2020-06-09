package domain.entity

import java.util.*

// Entity
class CustomerMitra(
    val id: Long,
    val mitraId: Long,
    val nama: String,
    val nomorHp: String,
    val transaksi: MutableList<Hutang> = mutableListOf()
) {
    fun buatHutangBaru(jumlahPinjaman: Long, deskripsi: String): Hutang {
        val newHutang = Hutang(
            id = null,
            deskripsi = deskripsi,
            jumlahPinjaman = jumlahPinjaman,
            tanggalPinjam = Date()
        )
        transaksi.add(newHutang)
        return newHutang
    }

    fun listHutangBelumLunas(): List<Hutang> =
        transaksi.filter { it.statusHutang() == StatusHutang.BELUM_LUNAS }

    fun listHutangLunas(): List<Hutang> =
        transaksi.filter { it.statusHutang() == StatusHutang.LUNAS }

    fun listSemuaHutang(): List<Hutang> = transaksi

    fun lunasiHutang(hutangId: Long) {
        transaksi.find { it.id == hutangId }?.lunasiHutang()
    }
}