package hu.bme.aut.httpretrofitdemo.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    @SerialName("camera")
    var camera: Camera? = Camera(),
    @SerialName("earth_date")
    var earthDate: String? = "",
    @SerialName("id")
    var id: Int? = 0,
    @SerialName("img_src")
    var imgSrc: String? = "",
    @SerialName("rover")
    var rover: Rover? = Rover(),
    @SerialName("sol")
    var sol: Int? = 0
)