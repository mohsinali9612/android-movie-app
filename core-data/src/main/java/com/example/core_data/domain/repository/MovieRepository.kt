package com.example.core_data.domain.repository

import com.example.core_data.datasource.network.result_state.ResultState
import com.example.core_data.domain.models.response_models.media.SearchMultiResponse
import com.example.core_data.domain.models.response_models.video.VideoDataResponse
import com.example.utils_extension.utils.ExtConstants
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun search(page:Int= ExtConstants.IntegerConstants.ONE, searchText: String): Flow<ResultState<SearchMultiResponse>>
    fun getMovieVideoData(id: Int): Flow<ResultState<VideoDataResponse>>
    fun getTvVideoData(id: Int): Flow<ResultState<VideoDataResponse>>

}