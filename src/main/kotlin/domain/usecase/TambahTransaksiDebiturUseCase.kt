package domain.usecase

import domain.data.TransaksiStorage
import domain.entity.Transaksi
import domain.usecase.TambahTransaksiDebiturUseCase.JenisTransaksi

interface TambahTransaksiDebiturUseCase {
    operator fun invoke()

    interface Presenter {
        fun presentSuccess(message: String)
        fun presentError(e: Exception)
    }

    data class TransactionData(
        val jenisTransaksi: JenisTransaksi,
        val amount: Long,
        val description: String,
        val krediturId: Long,
        val debiturId: Long
    )

    enum class JenisTransaksi {
        PINJAM, BAYAR
    }
}

class TambahTransaksiDebiturUseCaseInteractor(
    private val storage: TransaksiStorage,
    private val presenter: TambahTransaksiDebiturUseCase.Presenter,
    private val transactionData: TambahTransaksiDebiturUseCase.TransactionData
) : TambahTransaksiDebiturUseCase {
    override fun invoke() {
        try {
            val (jenisTransaksi, amount, description, krediturId, debiturId) = transactionData
            require(krediturId > 0)
            require(debiturId > 0)
            require(amount > 0)

            val transaksi = when (jenisTransaksi) {
                JenisTransaksi.PINJAM -> {
                    Transaksi.Pinjam(
                        amount = amount,
                        description = description,
                        kreditur = krediturId,
                        debitur = debiturId
                    )
                }
                JenisTransaksi.BAYAR -> {
                    Transaksi.Bayar(
                        amount = amount,
                        description = description,
                        kreditur = krediturId,
                        debitur = debiturId
                    )
                }
            }

            storage.store(transaksi)
            presenter.presentSuccess(transaksi.toString())
        } catch (e: Exception) {
            presenter.presentError(e)
        }
    }
}