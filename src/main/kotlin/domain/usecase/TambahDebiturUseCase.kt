package domain.usecase

import domain.entity.Debitur

/**
 * silahkan diberikan ke outer layer
 */
interface TambahDebiturUseCase {
    operator fun invoke()

    interface Presenter {
        fun presentError(e: Exception)
        fun presentSuccess(message: String)
    }
}

class TambahDebiturUseCaseInteractor(
    private val storage: DebiturStorage,
    private val presenter: TambahDebiturUseCase.Presenter
) : TambahDebiturUseCase {
    override fun invoke() {
//        try {
//            storage.store(debitur)
//            presenter.presentSuccess(debitur.toString())
//        } catch (e: Exception) {
//            presenter.presentError(e)
//        }
    }
}

interface DebiturStorage {
    fun store(debitur: Debitur)
}
