package com.example.movie_app.di

import com.example.core_data.di.ktorNetworkModule
import com.example.core_data.di.repositoryModule
import com.example.core_data.di.useCaseModule
import com.example.movie_app.MovieApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

fun initKoin(){
    startKoin {
        androidLogger()
        // Reference Android context
        androidContext(MovieApplication.getApplication())
        modules(
            configModule,
            ktorNetworkModule,
            coroutineDispatchersModule,
            viewModelModule,
            repositoryModule,
            useCaseModule
        )
    }
}