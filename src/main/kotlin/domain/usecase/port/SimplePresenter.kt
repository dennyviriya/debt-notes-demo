package domain.usecase.port

interface SimplePresenter {
    fun onSuccess()
    fun onError(exception: Exception)
    fun onComplete()
}
