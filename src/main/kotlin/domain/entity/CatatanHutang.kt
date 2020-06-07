package domain.entity

import java.util.*

class CatatanHutang(
    val id: Long,
    val mitraId: Long,
    val customerId: Long,
    private val transaksi: MutableList<Hutang> = mutableListOf()
) {
    fun buatHutangBaru(jumlahPinjaman: Long, deskripsi: String) {
        transaksi.add(
            Hutang(
                id = null,
                catatanHutangId = id,
                deskripsi = deskripsi,
                jumlahPinjaman = jumlahPinjaman,
                tanggalPinjam = Date()
            )
        )
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