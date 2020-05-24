package infrastructure.springmvc.model

data class Debitur(
    var id: Long? = null,
    var name: String = "",
    var phoneNumber: String = "",
    var address: String = ""
)