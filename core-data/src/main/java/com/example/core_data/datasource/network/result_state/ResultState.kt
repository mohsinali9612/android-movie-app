package com.example.core_data.datasource.network.result_state
import com.example.core_data.datasource.network.error_handling.AppException
import io.ktor.http.Headers


sealed class ResultState<out T> {
    class Success<T>(val data: T?, val headers: Headers) : ResultState<T>()
    class Error(val error: AppException) : ResultState<Nothing>()
    class SuccessWithErrorData<T>(val data: T?, val error: AppException, val headers: Headers) : ResultState<T>()
}

sealed class Resource<out T>{
    class Success<out T>(val data: T?) : Resource<T>()
    class Error<out T>(val message: String, data: T? = null) : Resource<T>()
    class Loading(val isLoading: Boolean) : Resource<Nothing>()
}