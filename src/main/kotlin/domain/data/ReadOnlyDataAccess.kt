package domain.data

interface ReadOnlyDataAccess<T> {
    suspend fun get(id: Long): T
    suspend fun getAll(): List<T>
}

interface MutableDataAccess<T> : ReadOnlyDataAccess<T> {
    suspend fun store(data: T)
    suspend fun store(data: List<T>)
}