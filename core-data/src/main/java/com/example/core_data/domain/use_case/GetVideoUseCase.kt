package com.example.core_data.domain.use_case

import com.example.core_data.datasource.enums.MediaTypeEnums
import com.example.core_data.domain.models.dto.VideoDTO
import com.example.core_data.domain.models.dto.toVideoDTO
import com.example.core_data.datasource.repository_imp.MovieRepositoryImp
import com.example.core_data.datasource.network.result_state.Resource
import com.example.core_data.datasource.network.result_state.ResultState
import com.example.core_data.domain.repository.MovieRepository
import com.example.utils_extension.utils.ExtConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class GetVideoUseCase(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(mediaType: String,id:Int): Flow<Resource<VideoDTO>> {

        val response = if (mediaType== MediaTypeEnums.MOVIE.mediaType)
            movieRepository.getMovieVideoData(id = id)
        else movieRepository.getTvVideoData(id=id)

        return response.map { resultState ->
            when (resultState) {
                is ResultState.Success -> {

                    if (resultState.data?.results?.isNotEmpty()==true){
                        Resource.Success(resultState.data.results.first().toVideoDTO())
                    }else{
                        Resource.Success(null)
                    }
                }
                is ResultState.Error -> Resource.Error(resultState.error.message ?: ExtConstants.StringConstants.EMPTY, null)
                is ResultState.SuccessWithErrorData -> Resource.Error(resultState.error.message ?: ExtConstants.StringConstants.EMPTY, resultState.data?.results?.first()?.toVideoDTO())
            }
        }.onStart {
            emit(Resource.Loading(true))
        }.catch {
            emit(Resource.Error(message = it.message ?: "Unknown Error"))
        }.onCompletion {
            emit(Resource.Loading(false))
        }
    }
}