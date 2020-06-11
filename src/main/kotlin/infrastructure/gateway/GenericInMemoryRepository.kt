package infrastructure.gateway

import domain.usecase.GenericRepository
import java.util.concurrent.atomic.AtomicLong
import kotlin.reflect.KClass

open class GenericInMemoryRepository : GenericRepository {
    protected val typeMap: MutableMap<String, MutableMap<Long, Any>> = mutableMapOf()
    protected val atomicLong: AtomicLong = AtomicLong()


    override fun <V : Any> add(value: V): Long {
        val type =  value::class.java.canonicalName
        if (typeMap.containsKey(type).not())
            typeMap[type] = mutableMapOf()

        val assignedId = atomicLong.incrementAndGet()
        typeMap[type]?.put(assignedId, value)
        return assignedId
    }

    override fun <V: Any> find(type: KClass<V>, predicate: (V) -> Boolean): V? {
        val javaType = type.java.canonicalName
        return typeMap[javaType]?.values?.toList()?.find {
            predicate(it as V)
        } as? V
    }
}