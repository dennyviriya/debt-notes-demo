package infrastructure.gateway

import domain.usecase.GenericRepository
import java.util.concurrent.atomic.AtomicLong

open class GenericInMemoryRepository : GenericRepository {
    protected val typeMap: MutableMap<String, MutableMap<Long, Any>> = mutableMapOf()
    protected val atomicLong: AtomicLong = AtomicLong()


    override fun <V : Any> add(type: String, value: V): Long {
        if (typeMap.containsKey(type).not())
            typeMap[type] = mutableMapOf()

        val assignedId = atomicLong.incrementAndGet()
        typeMap[type]?.put(assignedId, value)
        return assignedId
    }

    override fun <V : Any> find(type: String, predicate: (V) -> Boolean): V? {
        return typeMap[type]?.values?.toList()?.find {
            predicate(it as V)
        } as? V
    }
}