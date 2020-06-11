package domain.usecase

import domain.entity.CustomerMitra
import kotlin.reflect.KClass

// Gateway
interface GenericRepository {
    fun <V : Any> add(value: V):  Long
    fun <V: Any> find(type: KClass<V>, predicate: (V) -> Boolean): V?
}

