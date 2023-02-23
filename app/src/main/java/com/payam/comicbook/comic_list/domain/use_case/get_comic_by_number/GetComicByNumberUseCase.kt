package com.payam.comicbook.comic_list.domain.use_case.get_comic_by_number

import com.payam.comicbook.comic_list.data.entity.toModel
import com.payam.comicbook.comic_list.domain.model.ComicModel
import com.payam.comicbook.comic_list.domain.repository.ComicsRepository
import com.payam.comicbook.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

import javax.inject.Inject

class GetComicByNumberUseCase @Inject constructor(
    private val comicsRepository: ComicsRepository
) {

    operator fun invoke(number: Int): Flow<Resource<ComicModel>> = flow {

        try {
            emit(Resource.Loading())

            val comic = comicsRepository.getComicByNumber(number = number).toModel()

            emit(Resource.Success(data = comic))

        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server. Check your internet connection"
                )
            )
        }

    }

}