package com.payam.comicbook.comic_list.di

import com.payam.comicbook.comic_list.data.api.ComicsApiService
import com.payam.comicbook.comic_list.data.network.ComicsRepositoryImpl
import com.payam.comicbook.comic_list.domain.repository.ComicsRepository
import com.payam.comicbook.di.NormalRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ComicsModule {

    @Singleton
    @Provides
    fun provideComicsApi(
        @NormalRetrofitClient retrofit: Retrofit
    ): ComicsApiService {
        return retrofit.create(ComicsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideComicsRepository(api: ComicsApiService): ComicsRepository {
        return ComicsRepositoryImpl(api = api)
    }

}