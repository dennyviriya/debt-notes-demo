package domain.entity

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertFailsWith

class HutangTest {

    @Test
    fun createHutang_WithZeroAmount_ShouldThrowException() {
        assertFailsWith<IllegalArgumentException> {
            Hutang(id = 1, deskripsi = "", jumlahPinjaman = 0)
        }
    }

    @Test
    fun createHutang_WithNegativeAmount_ShouldThrowException() {
        assertFailsWith<IllegalArgumentException> {
            Hutang(id = 1, deskripsi = "", jumlahPinjaman = -10000)
        }
    }

    @Test
    fun createHutang_WithPositiveAmount_ShouldProceedCorrectly() {
        Hutang(id = 1, deskripsi = "", jumlahPinjaman = 10000)
    }

    @Test
    fun bayarHutang_WithZeroAmount_ShouldThrowException() {
        val hutang = Hutang(id = 1, deskripsi = "", jumlahPinjaman = 100000)
        assertFailsWith<IllegalArgumentException> {
            hutang.bayarHutang(0)
        }
    }

    @Test
    fun bayarHutang_WithNegativeAmount_ShouldThrowException() {
        val hutang = Hutang(id = 1, deskripsi = "", jumlahPinjaman = 100000)
        assertFailsWith<IllegalArgumentException> {
            hutang.bayarHutang(-1)
        }
    }

    @Test
    fun bayarHutang_AcceptanceTest1() { // Acceptance Test
        // given
        val jumlahPinjaman: Long = 100000
        val hutang = Hutang(id = 1, deskripsi = "", jumlahPinjaman = jumlahPinjaman)

        // when
        hutang.bayarHutang(10000)

        // then sisa hutang should be correct
        // then status hutang should be BELUM_LUNAS
        // then riwayat pembayaran size should be 1
        Assert.assertThat(hutang.jumlahPinjaman, equalTo(jumlahPinjaman))
        Assert.assertThat(hutang.sisaHutang(), equalTo(90000L))
        Assert.assertThat(hutang.statusHutang(), equalTo(StatusHutang.BELUM_LUNAS))
        Assert.assertThat(hutang.riwayatPembayaran().size, equalTo(1))
    }

    @Test
    fun lunasiHutang_AcceptanceTest2() { // Acceptance Test
        // given
        val jumlahPinjaman: Long = 100000
        val hutang = Hutang(id = 1, deskripsi = "", jumlahPinjaman = jumlahPinjaman)

        // when
        hutang.lunasiHutang()

        // then sisa hutang should be correct
        // then status hutang should be LUNAS
        // then riwayat pembayaran size should be 1
        Assert.assertThat(hutang.jumlahPinjaman, equalTo(jumlahPinjaman))
        Assert.assertThat(hutang.sisaHutang(), equalTo(0L))
        Assert.assertThat(hutang.statusHutang(), equalTo(StatusHutang.LUNAS))
        Assert.assertThat(hutang.riwayatPembayaran().size, equalTo(1))
    }
}