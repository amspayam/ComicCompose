package com.payam.comicbook.comic_list.domain.model

import java.util.*

data class ComicModel(

    val number: Int,

    // Text
    val title: String,
    val description: String,

    // Image
    val imageLink: String, // Address for save image in mobile device

    // Date
    val date: Calendar
)