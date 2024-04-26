package hu.bme.aut.httpretrofitdemo.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rover(
    @SerialName("cameras")
    var cameras: List<CameraX>? = listOf(),
    @SerialName("id")
    var id: Int? = 0,
    @SerialName("landing_date")
    var landingDate: String? = "",
    @SerialName("launch_date")
    var launchDate: String? = "",
    @SerialName("max_date")
    var maxDate: String? = "",
    @SerialName("max_sol")
    var maxSol: Int? = 0,
    @SerialName("name")
    var name: String? = "",
    @SerialName("status")
    var status: String? = "",
    @SerialName("total_photos")
    var totalPhotos: Int? = 0
)