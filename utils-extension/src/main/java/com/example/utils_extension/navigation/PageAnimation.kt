package com.example.utils_extension.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.example.utils_extension.utils.ExtConstants.AnimationConstants.PAGE_ANIM_DURATION
import com.example.utils_extension.utils.SlideDirection
import kotlin.reflect.KType


inline fun <reified T : Any> NavGraphBuilder.horizontallyAnimatedComposable(
    tween: Int = PAGE_ANIM_DURATION,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(
        content = content,
        typeMap = typeMap,
        enterTransition = {
            slideIntoContainer(SlideDirection.Left, animationSpec = tween(tween))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(tween))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(tween))
        },
        popExitTransition = {
            slideOutOfContainer(SlideDirection.Right, animationSpec = tween(tween))
        }
    )
}