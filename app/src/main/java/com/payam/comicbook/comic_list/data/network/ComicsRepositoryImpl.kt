package com.payam.comicbook.comic_list.data.network

import com.payam.comicbook.comic_list.data.api.ComicsApiService
import com.payam.comicbook.comic_list.domain.repository.ComicsRepository
import javax.inject.Inject

class ComicsRepositoryImpl @Inject constructor(
    private val api: ComicsApiService
) : ComicsRepository {
    override suspend fun getLastComic() = api.getLastComics()

    override suspend fun getComicByNumber(number: Int) = api.getComicByNumber(number = number)
}