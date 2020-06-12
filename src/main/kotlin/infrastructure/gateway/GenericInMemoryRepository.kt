package infrastructure.gateway

import domain.usecase.GenericRepository
import java.util.concurrent.atomic.AtomicLong
import kotlin.reflect.KClass

open class GenericInMemoryRepository : GenericRepository {
    protected val storage: MutableMap<Int, MutableMap<Long, Any>> = mutableMapOf()
    protected val atomicLong: AtomicLong = AtomicLong()


    override fun <V : Any> add(value: V): Long {
        val itemType: Int = value::class.hashCode()
        if (storage.containsKey(itemType).not())
            storage[itemType] = mutableMapOf()

        val assignedId = atomicLong.incrementAndGet()
        storage[itemType]?.put(assignedId, value)
        return assignedId
    }

    override fun <V : Any> find(type: KClass<V>, predicate: (V) -> Boolean): V? {
        val itemType: Int = type.hashCode()
        return storage[itemType]?.values?.toList()?.find {
            predicate(it as V)
        } as? V
    }
}