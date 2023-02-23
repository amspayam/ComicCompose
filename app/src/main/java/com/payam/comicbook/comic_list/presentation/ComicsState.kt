package com.payam.comicbook.comic_list.presentation

import com.payam.comicbook.comic_list.domain.model.ComicModel

data class ComicsState(
    val isLoading: Boolean = false,
    val comic: ComicModel? = null,
    val error: String = ""
)