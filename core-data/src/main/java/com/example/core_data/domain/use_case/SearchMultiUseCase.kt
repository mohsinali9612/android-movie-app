package com.example.core_data.domain.use_case

import com.example.core_data.domain.models.dto.SearchMultiDTO
import com.example.core_data.domain.models.dto.toSearchMultiDTO
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

class SearchMultiUseCase(
   private val movieRepository: MovieRepository
) {
    operator fun invoke(searchText: String,page:Int): Flow<Resource<SearchMultiDTO>> {
        return movieRepository.search(searchText =searchText, page = page).map { resultState ->
            when (resultState) {
                is ResultState.Success -> Resource.Success(resultState.data?.toSearchMultiDTO())
                is ResultState.Error -> {
                    Resource.Error(resultState.error.message ?: ExtConstants.StringConstants.EMPTY, null)
                }
                is ResultState.SuccessWithErrorData -> Resource.Error(resultState.error.message ?: ExtConstants.StringConstants.EMPTY, resultState.data?.toSearchMultiDTO())
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