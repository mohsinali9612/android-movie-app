package com.example.core_data.datasource.repository_imp

import com.example.core_data.datasource.network.api_urls.ApiEndpoints
import com.example.core_data.datasource.network.result_state.ResultState
import com.example.core_data.domain.models.response_models.media.SearchMultiResponse
import com.example.core_data.domain.models.response_models.video.VideoDataResponse
import com.example.core_data.datasource.network.safe_call.safeNetworkCall
import com.example.core_data.domain.repository.MovieRepository
import com.example.utils_extension.utils.ExtConstants
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

class MovieRepositoryImp(
    private val httpClient: HttpClient,
    private val apiKey:String,
) : MovieRepository {


    override fun search(page:Int, searchText: String) = channelFlow {
        val result = safeNetworkCall<SearchMultiResponse>(
            block = httpClient.get(urlString = ApiEndpoints.SearchMulti) {
                parameter("api_key", apiKey)
                parameter("query", searchText)
                parameter("page", page)

            }
        )
        send(result)
    }

    override fun getMovieVideoData(id: Int) = channelFlow {
        val result = safeNetworkCall<VideoDataResponse>(
            block = httpClient.get(urlString = ApiEndpoints.MovieVideo) {
                url {
                    appendPathSegments("${id}")
                    appendPathSegments(ApiEndpoints.Videos)
                    parameters.append("api_key", apiKey)
                }
            }
        )
        send(result)
    }

    override fun getTvVideoData(id: Int) = channelFlow {
        val result = safeNetworkCall<VideoDataResponse>(
            block = httpClient.get(urlString = ApiEndpoints.TvVideo) {
                url {
                    appendPathSegments("${id}")
                    appendPathSegments(ApiEndpoints.Videos)

                    parameters.append("api_key", apiKey)
                }
            }
        )
        send(result)
    }

}