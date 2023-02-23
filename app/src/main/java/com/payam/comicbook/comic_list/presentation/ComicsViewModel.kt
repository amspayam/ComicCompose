package com.payam.comicbook.comic_list.presentation

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payam.comicbook.comic_list.domain.use_case.get_comic_by_number.GetComicByNumberUseCase
import com.payam.comicbook.comic_list.domain.use_case.get_last_comics.GetLastComicsUseCase
import com.payam.comicbook.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ComicsViewModel @Inject constructor(
    private val getLastComicsUseCase: GetLastComicsUseCase,
    private val getComicByNumberUseCase: GetComicByNumberUseCase
) : ViewModel() {

    private val _comicState = mutableStateOf(ComicsState())
    val comicsState: State<ComicsState> = _comicState

    var firstComicNumber = 1
    var lastComicNumber = 0
    var currentComic = 0

    init {
        getLastComic()
    }

    fun refresh() {
        getComicByNumber(currentComic)
    }

    @VisibleForTesting
    private fun getLastComic() {
        getLastComicsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        lastComicNumber = it.number
                        currentComic = it.number
                    }
                    _comicState.value = ComicsState(comic = result.data)
                }
                is Resource.Error -> {
                    _comicState.value = ComicsState(
                        error = result.message ?: "An unexpected error occurred."
                    )
                }
                is Resource.Loading -> {
                    _comicState.value = ComicsState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getComicByNumber(number: Int) {
        if (number in (firstComicNumber)..lastComicNumber) {
            currentComic = number
            getComicByNumberUseCase(number = number).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _comicState.value = ComicsState(comic = result.data)
                    }
                    is Resource.Error -> {
                        _comicState.value = ComicsState(
                            error = result.message ?: "An unexpected error occurred."
                        )
                    }
                    is Resource.Loading -> {
                        _comicState.value = ComicsState(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}