package hu.bme.aut.httpretrofitdemo.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.httpretrofitdemo.network.MarsAPI
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                Json{
                    ignoreUnknownKeys = true
                }.asConverterFactory("application/json".toMediaType())
            )
            .baseUrl("https://api.nasa.gov/")
            .build()
    }

    @Provides
    @Singleton
    fun provideMarsAPI(retrofit: Retrofit): MarsAPI {
        return retrofit.create(MarsAPI::class.java)
    }
}