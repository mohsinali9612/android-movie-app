package com.example.movie_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.toRoute
import com.example.core_data.domain.models.response_models.media.MediaDataModel
import com.example.movie_app.presentation.screens.detail.DetailScreen
import com.example.movie_app.presentation.screens.main_home.MainHomeScreen
import com.example.movie_app.presentation.screens.main_home.MainHomeScreenViewModel
import com.example.movie_app.presentation.screens.video_player.VideoPlayerScreen
import com.example.utils_extension.navigation.horizontallyAnimatedComposable
import com.example.utils_extension.navigation.typeMapOf
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHostGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.MainHome
    ) {
        horizontallyAnimatedComposable<AppRoutes.MainHome>(content = {
            val viewModel = koinViewModel<MainHomeScreenViewModel>()
            MainHomeScreen(navController = navController, dataState = viewModel.state, uiEvents = viewModel.eventFlow) {
                viewModel.onIntent(it)
            }
        })


        horizontallyAnimatedComposable<AppRoutes.Detail>(
            typeMap = mapOf(
                typeMapOf<MediaDataModel>()
            )
        ) {
            DetailScreen(navController = navController, mediaDataModel = it.toRoute<AppRoutes.Detail>().mediaDataModel)
        }

        horizontallyAnimatedComposable<AppRoutes.VideoPlayer>(content = {
            it.toRoute<AppRoutes.VideoPlayer>().apply {
                VideoPlayerScreen(mediaType = mediaType,id=id)
            }
        })
    }
}