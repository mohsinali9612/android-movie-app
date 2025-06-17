package com.example.movie_app.di

import com.example.movie_app.datasource.utils.ApiConfig
import com.example.utils_extension.utils.ExtConstants
import org.koin.core.qualifier.named
import org.koin.dsl.module

val configModule= module {
    single<String>(named(ExtConstants.Config.MOVIE_API)) {
        ApiConfig.TMDB_API_KEY 
    }
    single<String>(named(ExtConstants.Config.MOVIE_ACCESS_TOKEN)) {
        ApiConfig.TMDB_ACCESS_TOKEN 
    }
}