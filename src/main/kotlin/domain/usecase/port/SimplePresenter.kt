package domain.usecase.port

interface SimplePresenter {

    fun onSuccess()

    fun onError(exception: Exception)

    /**
     * called either success or error
     */
    fun onComplete()
}
