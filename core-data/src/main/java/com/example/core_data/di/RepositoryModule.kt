package com.example.core_data.di

import com.example.core_data.datasource.repository_imp.MovieRepositoryImp
import com.example.core_data.domain.repository.MovieRepository
import com.example.utils_extension.utils.ExtConstants
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> {
        MovieRepositoryImp(
            httpClient = get(),
            apiKey = get(named(ExtConstants.Config.MOVIE_API))
        ) as MovieRepository
    }
}