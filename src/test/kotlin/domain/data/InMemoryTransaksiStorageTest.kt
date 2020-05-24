package domain.data

import domain.entity.Debitur
import domain.entity.Kreditur
import domain.entity.Transaksi
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class InMemoryTransaksiStorageTest {

    private lateinit var storage: TransaksiStorage
    private lateinit var fakeMap: MutableMap<Long, Transaksi>
    private val kreditur = Kreditur(1, "Mitra Kesayangan")
    private val debitur = Debitur(1, "Tetangga Warung", "08123456789", "Jl. raya no.1A")
    private val desc = "rokok 1 dus"
    private val amount = 100000L

    @Before
    fun setUp() {
        fakeMap = TransaksiSingletonMap
        storage = InMemoryTransaksiStorage(fakeMap)
    }

    @After
    fun tearDown() {
        fakeMap.clear()
    }

    @Test
    fun storePinjaman() {
        val pinjam =
            Transaksi.Pinjam(amount = amount, description = desc, kreditur = kreditur.id, debitur = debitur.id!!)

        storage.store(pinjam)
        assertThat(fakeMap.size, equalTo(1))
    }

    @Test(expected = IllegalArgumentException::class)
    fun storePinjamanWithNotExistingIdShouldThrowError() {
        val pinjam =
            Transaksi.Pinjam(id = 100, amount = amount, description = desc, kreditur = kreditur.id, debitur = debitur.id!!)

        storage.store(pinjam)
    }

    @Test
    fun storePinjamanWithExistingIdShouldMutateExistingRecord() {
        val pinjam =
            Transaksi.Pinjam(amount = amount, description = desc, kreditur = kreditur.id, debitur = debitur.id!!)

        storage.store(pinjam)

        val newAmount = 500000L
        val pinjam2 =
            Transaksi.Pinjam(id = 1, amount = newAmount, description = desc, kreditur = kreditur.id, debitur = debitur.id!!)

        storage.store(pinjam2)

        assertThat(fakeMap.size, equalTo(1))
        assertThat(fakeMap.values.first().amount,  equalTo(newAmount))
    }
}