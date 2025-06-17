package com.example.movie_app.di

import com.example.utils_extension.enums.CoroutineDispatcherModuleEnums
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutineDispatchersModule= module {
    single<CoroutineDispatcher>(named(CoroutineDispatcherModuleEnums.DEFAULT.dispatcherName)) { Dispatchers.Default }
    single<CoroutineDispatcher>(named(CoroutineDispatcherModuleEnums.IO.dispatcherName)) { Dispatchers.IO }
    single<CoroutineDispatcher>(named(CoroutineDispatcherModuleEnums.MAIN.dispatcherName)) { Dispatchers.Main }
    single<CoroutineDispatcher>(named(CoroutineDispatcherModuleEnums.MAIN_IMMEDIATE.dispatcherName)) { Dispatchers.Main.immediate}
}