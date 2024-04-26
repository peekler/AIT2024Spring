package hu.bme.aut.httpmoneydemo.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.httpmoneydemo.network.MoneyAPI
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoneyApiModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://data.fixer.io/")
            .addConverterFactory(Json{ ignoreUnknownKeys = true }.asConverterFactory(
                "application/json".toMediaType()) )
            //.addConverterFactory(ScalarsConverterFactory.create()) // raw string result parsing..
            .build()
    }

    @Provides
    @Singleton
    fun provideMoneyAPI(retrofit: Retrofit): MoneyAPI {
        return retrofit.create(MoneyAPI::class.java)
    }
}