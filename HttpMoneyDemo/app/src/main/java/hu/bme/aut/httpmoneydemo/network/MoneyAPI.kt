package hu.bme.aut.httpmoneydemo.network

import hu.bme.aut.httpmoneydemo.data.MoneyResult
import retrofit2.http.GET
import retrofit2.http.Query


// http://data.fixer.io/api/latest?access_key=969c37b5a73f8cb2d12c081dcbdc35e6
// HOST: http://data.fixer.io
// PATH: /api/latest
// QUERY params: ?access_key=969c37b5a73f8cb2d12c081dcbdc35e6


interface MoneyAPI {
    @GET("api/latest")
    suspend fun getRates(@Query("access_key") accessKey: String): MoneyResult
}