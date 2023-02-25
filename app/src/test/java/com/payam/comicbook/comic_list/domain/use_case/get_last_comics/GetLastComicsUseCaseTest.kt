package com.payam.comicbook.comic_list.domain.use_case.get_last_comics

import com.payam.comicbook.comic_list.data.entity.ComicEntity
import com.payam.comicbook.comic_list.domain.repository.ComicsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class GetLastComicsUseCaseTest {

    private val comicsRepository: ComicsRepository = mockk()

    private lateinit var comicsUseCase: GetLastComicsUseCase

    @Before
    fun setUp() {
        comicsUseCase = GetLastComicsUseCase(comicsRepository)
    }

    @Test
    fun invoke() = runTest {
        // Data
        coEvery {
            comicsRepository.getLastComic()
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
        val result = comicsUseCase().first()

        // Assert
        assertEquals(10, result.getOrNull()?.number)


    }

}