package domain.usecase

// Gateway
interface GenericRepository {
    fun <V : Any> add(type: String, value: V):  Long
    fun <V: Any> find(type: String, predicate: (V) -> Boolean): V?
}

