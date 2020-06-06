package domain.usecase.port

interface ValuePresenter<T> {
    fun onCompleted(result: Result<T>)
}