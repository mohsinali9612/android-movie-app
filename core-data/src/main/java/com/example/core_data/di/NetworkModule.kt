package com.example.core_data.di

import com.example.core_data.datasource.network.interceptor.AuthInterceptor
import com.example.core_data.datasource.network.network_client.KtorHttpClient
import com.example.utils_extension.utils.ExtConstants
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.qualifier.named
import org.koin.dsl.module


val ktorNetworkModule = module {
    single<HttpClientEngine> { OkHttp.create() }
    single { AuthInterceptor(apiKey = get(qualifier = named(ExtConstants.Config.MOVIE_ACCESS_TOKEN)),)}
    single<HttpClient>() {
        KtorHttpClient(
            engine = get(),
            authInterceptor = get(),
        ).createKtorHttpClient()
    }
}
