package com.payam.comicbook.comic_list.data.network

import com.payam.comicbook.comic_list.data.api.ComicsApiService
import com.payam.comicbook.comic_list.data.entity.ComicEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class ComicsRepositoryImplTest {

    private val comicApiService: ComicsApiService = mockk()

    private lateinit var comicsRepositoryImpl: ComicsRepositoryImpl

    @Before
    fun setUp() {
        comicsRepositoryImpl = ComicsRepositoryImpl(api = comicApiService)
    }

    @Test
    fun getLastComic() = runTest {
        // Data
        coEvery {
            comicApiService.getLastComics()
        } coAnswers {
            ComicEntity(
                number = 10,
                link = null,
                news = null,
                safeTitle = null,
                transcript = null,
                alt = null,
                imageLink = null,
                year = null,
                month = null,
                day = null
            )
        }

        // Action
        val result = comicsRepositoryImpl.getLastComic()

        // Assert
        assertEquals(10, result.number)
    }

    @Test
    fun getComicByNumber() = runTest {
        // Data
        coEvery {
            comicApiService.getComicByNumber(number = 10)
        } coAnswers {
            ComicEntity(
                number = 10,
                link = null,
                news = null,
                safeTitle = null,
                transcript = null,
                alt = null,
                imageLink = null,
                year = null,
                month = null,
                day = null
            )
        }

        // Action
        val result = comicsRepositoryImpl.getComicByNumber(number = 10)

        // Assert
        assertEquals(10, result.number)

    }
}