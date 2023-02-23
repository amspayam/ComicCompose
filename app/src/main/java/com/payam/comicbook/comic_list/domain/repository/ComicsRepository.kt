package com.payam.comicbook.comic_list.domain.repository

import com.payam.comicbook.comic_list.data.entity.ComicEntity

interface ComicsRepository {
    suspend fun getLastComic(): ComicEntity
    suspend fun getComicByNumber(number: Int): ComicEntity
}