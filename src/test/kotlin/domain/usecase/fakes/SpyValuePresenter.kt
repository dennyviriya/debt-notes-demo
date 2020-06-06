package domain.usecase.fakes

import domain.usecase.port.ValuePresenter

class SpyValuePresenter<T> : ValuePresenter<T> {
    var successResult: T? = null

    override fun onCompleted(result: Result<T>) {
        successResult = result.getOrThrow()
    }
}