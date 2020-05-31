package domain.usecase.createloan

import domain.usecase.port.SimplePresenter

class SpySimplePresenter : SimplePresenter {
    val state: MutableList<String> = mutableListOf()

    override fun onSuccess() {
        state += "success"
    }

    override fun onError(exception: Exception) {
        state += "error"
    }

    override fun onComplete() {
        state += "complete"
    }
}