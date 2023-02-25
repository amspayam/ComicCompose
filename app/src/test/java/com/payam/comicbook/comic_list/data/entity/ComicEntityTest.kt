package com.payam.comicbook.comic_list.data.entity

import com.payam.comicbook.comic_list.domain.model.ComicModel
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ComicEntityTest {

    @Test
    fun `get comic entity from server, convert to entity model successfully`() {
        val expectation = ComicModel(
            number = 10,
            title = "",
            description = "",
            imageLink = "",
            date = ""
        )

        val comicEntity = ComicEntity(
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

        val result = comicEntity.toModel()

        assertEquals(expectation.number, result.number)

    }

}