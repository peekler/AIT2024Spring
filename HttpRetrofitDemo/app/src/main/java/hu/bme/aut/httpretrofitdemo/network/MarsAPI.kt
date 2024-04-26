package hu.bme.aut.httpretrofitdemo.network

import hu.bme.aut.httpretrofitdemo.data.RoverPhotos
import retrofit2.http.GET
import retrofit2.http.Query

//
// HOST: https://api.nasa.gov/
// PATH: mars-photos/api/v1/rovers/curiosity/photos
// QUERY params:
// ?
// earth_date=2024-1-4
// &
// api_key=DEMO_KEY


interface MarsAPI {
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getRoverPhotos(@Query("earth_date") earthDate: String,
                       @Query("api_key") apiKey: String) : RoverPhotos
}
