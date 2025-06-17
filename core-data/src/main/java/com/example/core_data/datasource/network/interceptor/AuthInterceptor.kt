package com.example.core_data.datasource.network.interceptor


import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.request.header
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch

class AuthInterceptor(
    private val apiKey:String,
) {
     fun intercept(builder: DefaultRequest.DefaultRequestBuilder){
         val job = Job()
         CoroutineScope(job+Dispatchers.IO).launch {
             builder.header("Authorization","Bearer ${apiKey}")
             job.cancelAndJoin()
         }

    }
}