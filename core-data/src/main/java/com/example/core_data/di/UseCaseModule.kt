package com.example.core_data.di

import com.example.core_data.domain.use_case.GetVideoUseCase
import com.example.core_data.domain.use_case.SearchMultiUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { SearchMultiUseCase(
        get(),
    ) }
    single { GetVideoUseCase(
        get(),
    ) }
}