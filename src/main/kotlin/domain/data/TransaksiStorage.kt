package domain.data

import domain.entity.Transaksi
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

interface TransaksiStorage {
    fun store(transaksi: Transaksi)
}

object TransaksiSingletonMap : MutableMap<Long, Transaksi> by ConcurrentHashMap()

class InMemoryTransaksiStorage(
    private val map: MutableMap<Long, Transaksi> = TransaksiSingletonMap
) : TransaksiStorage {

    private val mId = AtomicLong(1)

    private fun populateId(): Long = mId.getAndIncrement()

    override fun store(transaksi: Transaksi) {
        val id: Long = if (transaksi.id != null) {
            require(map.contains(transaksi.id))
            transaksi.id
        } else {
            populateId()
        }

        map[id] = transaksi
    }
}