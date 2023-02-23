package com.payam.comicbook.di

import com.payam.comicbook.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class IsDebugBuild

@Qualifier
annotation class LoggerInterceptorProvider

@Qualifier
annotation class AuthInterceptorProvider

@Qualifier
annotation class AuthOkHttpClient

@Qualifier
annotation class NormalOkHttpClient

@Qualifier
annotation class AuthRetrofitClient

@Qualifier
annotation class NormalRetrofitClient

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @IsDebugBuild
    @Singleton
    @Provides
    fun provideIsDebugBuild(): Boolean = BuildConfig.DEBUG

    @LoggerInterceptorProvider
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @NormalOkHttpClient
    @Singleton
    @Provides
    fun provideNormalOkHttpClient(
        @LoggerInterceptorProvider loggerInterceptor: Interceptor
    ): OkHttpClient = clientBuilder(
        loggerInterceptor = loggerInterceptor
    )

    @AuthOkHttpClient
    @Singleton
    @Provides
    fun provideAuthOkHttpClient(
        @LoggerInterceptorProvider loggerInterceptor: Interceptor,
        @AuthInterceptorProvider authInterceptor: Interceptor,
        tokenAuthenticator: Authenticator,
    ): OkHttpClient = clientBuilder(
        loggerInterceptor = loggerInterceptor,
        authInterceptor = authInterceptor,
        tokenAuthenticator = tokenAuthenticator
    )

    @NormalRetrofitClient
    @Singleton
    @Provides
    fun provideNormalRetrofitClient(
        @NormalOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit = retrofitBuilder(okHttpClient)

    @AuthRetrofitClient
    @Singleton
    @Provides
    fun provideAuthRetrofitClient(
        @AuthOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit = retrofitBuilder(okHttpClient)

    private fun retrofitBuilder(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .baseUrl("https://xkcd.com/")
        .build()

    private fun clientBuilder(
        loggerInterceptor: Interceptor,
        authInterceptor: Interceptor? = null,
        tokenAuthenticator: Authenticator? = null,
    ): OkHttpClient {
        val client = OkHttpClient.Builder().apply {
            if (authInterceptor != null)
                addInterceptor(authInterceptor)
            if (tokenAuthenticator != null)
                authenticator(tokenAuthenticator)
            addInterceptor(loggerInterceptor)

            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            connectTimeout(30, TimeUnit.SECONDS)
        }
        return client.build()
    }
}
