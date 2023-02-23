package com.payam.comicbook.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNormalOkHttpClient(
        loggerInterceptor: Interceptor
    ): OkHttpClient = clientBuilder(
        loggerInterceptor = loggerInterceptor
    )

    @Singleton
    @Provides
    fun provideNormalRetrofitClient(
        okHttpClient: OkHttpClient
    ): Retrofit = retrofitBuilder(okHttpClient)

    private fun retrofitBuilder(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .baseUrl("https://xkcd.com/")
        .build()

    private fun clientBuilder(
        loggerInterceptor: Interceptor
    ): OkHttpClient {
        val client = OkHttpClient.Builder().apply {
            addInterceptor(loggerInterceptor)

            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            connectTimeout(30, TimeUnit.SECONDS)
        }
        return client.build()
    }
}