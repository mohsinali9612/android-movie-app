package com.example.movie_app.di

import com.example.movie_app.presentation.screens.main_home.MainHomeScreenViewModel
import com.example.movie_app.presentation.screens.video_player.VideoPlayerViewModel
import com.example.utils_extension.enums.CoroutineDispatcherModuleEnums
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<MainHomeScreenViewModel>{
        MainHomeScreenViewModel(
            get(named(CoroutineDispatcherModuleEnums.IO.dispatcherName)),
            get()
            )
    }
    viewModel<VideoPlayerViewModel>{
        VideoPlayerViewModel(
            ioDispatcher =  get(named(CoroutineDispatcherModuleEnums.IO.dispatcherName)),
            mainDispatcher = get(named(CoroutineDispatcherModuleEnums.MAIN.dispatcherName)),
            getVideoDataUseCase =  get()
        )
    }
}