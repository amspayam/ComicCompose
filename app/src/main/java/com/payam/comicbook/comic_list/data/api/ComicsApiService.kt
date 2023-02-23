package com.payam.comicbook.comic_list.data.api

import com.payam.comicbook.comic_list.data.entity.ComicEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicsApiService {

    @GET("info.0.json")
    suspend fun getLastComics(): ComicEntity

    @GET("{number}/info.0.json")
    suspend fun getComicByNumber(@Path("number") number: Int): ComicEntity

}