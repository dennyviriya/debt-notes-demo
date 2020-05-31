package interface_adapter

import domain.usecase.port.GenericRepository
import java.util.concurrent.atomic.AtomicLong

open class GenericInMemoryRepository<V> : GenericRepository<V> {
    protected val map: MutableMap<Long, V> = mutableMapOf()
    protected val atomicLong: AtomicLong =
        AtomicLong()

    override fun add(value: V) {
        map[atomicLong.incrementAndGet()] = value
    }

    override fun add(id: Long, value: V) {
        if (map.containsKey(id))
            map[id] = value
        else
            throw IllegalArgumentException("id must only be used on existing value")
    }

    override fun remove(id: Long) {
        map.remove(id)
    }

    override fun get(id: Long): V {
        return map[id]!!
    }

    override fun find(predicate: (V) -> Boolean): List<V> {
        return map.values.toList().filter(predicate)
    }
}