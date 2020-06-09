package domain.entity

import java.io.Serializable

// Entity
class Mitra(
    val id: Long,
    val nama: String,
    val listCustomerMitra: List<CustomerMitra>
) {
    @Throws(IllegalArgumentException::class)
    fun tambahHutang(customerMitraId: Long, jumlah: Long, deskripsi: String) {
        val customerMitra = listCustomerMitra.find { it.id == customerMitraId }
        requireNotNull(customerMitra)
        customerMitra.buatHutangBaru(jumlahPinjaman = jumlah, deskripsi = deskripsi)
    }

    fun getSemuaHutangCustomer(): List<CustomerMitra> = listCustomerMitra
}