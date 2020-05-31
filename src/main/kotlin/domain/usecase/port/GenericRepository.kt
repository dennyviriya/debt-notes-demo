package domain.usecase.port

interface GenericRepository<V> {
    fun add(value: V)
    fun add(id: Long, value: V)
    fun remove(id: Long)
    fun get(id: Long): V
    fun find(predicate: (V) -> Boolean): List<V>
}

