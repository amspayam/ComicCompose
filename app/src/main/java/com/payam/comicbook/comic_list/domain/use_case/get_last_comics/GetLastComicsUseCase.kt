package com.payam.comicbook.comic_list.domain.use_case.get_last_comics

import com.payam.comicbook.comic_list.data.entity.toModel
import com.payam.comicbook.comic_list.domain.model.ComicModel
import com.payam.comicbook.comic_list.domain.repository.ComicsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetLastComicsUseCase @Inject constructor(
    private val comicsRepository: ComicsRepository
) {

    operator fun invoke(): Flow<Result<ComicModel>> = flow {
        try {

            val comic = comicsRepository.getLastComic().toModel()

            Result.success(comic)

            emit(Result.success(comic))

        } catch (e: HttpException) {
            emit(
                Result.failure(
                    Throwable(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            )
        } catch (e: IOException) {
            emit(
                Result.failure(
                    Throwable(
                        "Couldn't reach server. Check your internet connection"
                    )
                )
            )
        }
    }
}