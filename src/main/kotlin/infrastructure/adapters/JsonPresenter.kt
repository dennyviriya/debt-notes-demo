package infrastructure.adapters

import com.fasterxml.jackson.databind.ObjectMapper
import domain.usecase.HutangCreatedResponseModel
import domain.usecase.Presenter

class JsonPresenter(val viewModel: ViewModel) : Presenter {
    private val jsonMapper = ObjectMapper()

    override fun onHutangCreated(response: HutangCreatedResponseModel) {
        viewModel.value = jsonMapper.writeValueAsString(response)
    }

    override fun onFailCreateHutang(exception: Exception) {
        viewModel.error = exception
    }

    data class ViewModel(
        var value: String? = null,
        var error: Exception? = null
    )
}