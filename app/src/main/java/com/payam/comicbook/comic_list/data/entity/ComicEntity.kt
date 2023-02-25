package com.payam.comicbook.comic_list.data.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.payam.comicbook.comic_list.domain.model.ComicModel
import java.util.*

@Keep
data class ComicEntity(
    @SerializedName("num")
    val number: Int?,
    val link: String?,
    val news: String?,

    // Text
    @SerializedName("safe_title")
    val safeTitle: String?,
    val transcript: String?,
    val alt: String?,

    // Image
    @SerializedName("img")
    val imageLink: String?,

    // Date
    val year: Int?,
    val month: Int?,
    val day: Int?,
)

fun ComicEntity.toModel() = ComicModel(
    number = number ?: 0,
    title = safeTitle ?: "",
    description = alt ?: "",
    imageLink = imageLink ?: "",
    date = "$year - $month - $day"
)
