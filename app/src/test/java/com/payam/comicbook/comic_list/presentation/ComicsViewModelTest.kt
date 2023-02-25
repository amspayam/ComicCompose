package com.payam.comicbook.comic_list.presentation

import com.payam.comicbook.comic_list.domain.model.ComicModel
import com.payam.comicbook.comic_list.domain.use_case.get_comic_by_number.GetComicByNumberUseCase
import com.payam.comicbook.comic_list.domain.use_case.get_last_comics.GetLastComicsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class ComicsViewModelTest {

    @MockK(relaxed = true)
    private lateinit var getLastComicsUseCase: GetLastComicsUseCase

    @MockK(relaxed = true)
    private lateinit var getComicByNumberUseCase: GetComicByNumberUseCase

    private lateinit var viewModel: ComicsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = ComicsViewModel(getLastComicsUseCase, getComicByNumberUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `call getLastComicUseCase, success response`() = runTest {
        //Given
        val expectation = Result.success(
            ComicModel(
                number = 10,
                title = "",
                description = "",
                imageLink = "",
                date = ""
            )
        )

        coEvery {
            getLastComicsUseCase()
        } returns flow {
            emit(expectation)
        }

        //When
        viewModel.getLastComic()

        //Verify
        assertEquals(ComicsState(comic = expectation.getOrNull()), viewModel.comicsState.value)

    }

    @Test
    fun `call getComicByNumberUseCase, success response`() = runTest {
        //Given
        viewModel.lastComicNumber = 120
        viewModel.currentComic = 120
        val expectation = Result.success(
            ComicModel(
                number = 10,
                title = "",
                description = "",
                imageLink = "",
                date = ""
            )
        )

        coEvery {
            getComicByNumberUseCase(10)
        } returns flow {
            emit(expectation)
        }

        //When
        viewModel.getComicByNumber(10)

        //Verify
        assertEquals(ComicsState(comic = expectation.getOrNull()), viewModel.comicsState.value)
    }

}