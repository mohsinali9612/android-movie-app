package com.example.movie_app.presentation.screens.video_player

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.example.movie_app.R
import com.example.utils_extension.safe_click.clickableSingleWithoutRipple
import com.example.utils_extension.utils.ExtConstants
import org.koin.androidx.compose.koinViewModel

@Composable
fun VideoPlayerScreen(
    mediaType: String,
    id:Int,
    viewModel: VideoPlayerViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val activity = context as Activity


    LaunchedEffect(Unit) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }


    LaunchedEffect(id) {
        viewModel.initializePlayer(context=context)
    }


    DisposableEffect(Unit) {
        onDispose {
            // Reset to portrait orientation
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            // Release player resources
            viewModel.releasePlayer()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        viewModel.exoPlayer?.let { player ->
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        this.player = player
                        useController = false // Hide default controls
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        Image(
            painter = painterResource( if (viewModel.isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
            contentDescription = ExtConstants.StringConstants.EMPTY,
            modifier = Modifier
                .align(Alignment.Center)
                .size(64.dp)
                .clickableSingleWithoutRipple{
                    viewModel.togglePlayPause()
                }
        )


        if (viewModel.isBuffering) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(48.dp),
                color = Color.White
            )
        }
    }
} 