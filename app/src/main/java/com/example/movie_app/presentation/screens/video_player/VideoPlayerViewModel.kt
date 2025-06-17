package com.example.movie_app.presentation.screens.video_player

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.core_data.datasource.network.result_state.Resource
import com.example.core_data.domain.use_case.GetVideoUseCase
import com.example.utils_extension.utils.ExtConstants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class VideoPlayerViewModel(
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher,
    private val getVideoDataUseCase: GetVideoUseCase
) : ViewModel() {
    private var _exoPlayer: ExoPlayer? = null
    val exoPlayer: ExoPlayer?
        get() = _exoPlayer

    var playbackPosition by mutableStateOf(0L)
        private set

    var playWhenReady by mutableStateOf(true)
        private set

    var isBuffering by mutableStateOf(true)
        private set

    var isPlaying by mutableStateOf(false)
        private set

    fun initializePlayer(context: Context, videoUri: String= ExtConstants.StringConstants.EMPTY) {
        if (_exoPlayer == null) {
            _exoPlayer = ExoPlayer.Builder(context).build().apply {
                // Create MediaItem with proper configuration
                val mediaItem = MediaItem.Builder()
                    .setUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
                    .build()

                setMediaItem(mediaItem)
                prepare()
                seekTo(playbackPosition)
                this.playWhenReady = this@VideoPlayerViewModel.playWhenReady

                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        isBuffering = (state == Player.STATE_BUFFERING)
                        this@VideoPlayerViewModel.isPlaying = (state == Player.STATE_READY && playWhenReady)
                    }

                    override fun onPlayerError(error: PlaybackException) {
                        super.onPlayerError(error)
                        // Handle playback error
                        error.printStackTrace()
                    }
                })
            }
        }
    }

    fun getVideoData(context: Context, mediaType: String, id: Int) {
        getVideoDataUseCase.invoke(mediaType = mediaType, id = id)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        withContext(mainDispatcher) {
                            // Ensure we have a valid video URL
                            val videoUrl = result.data?.videoUrl
                            if (!videoUrl.isNullOrEmpty()) {
                                initializePlayer(context = context, videoUri = videoUrl)
                            }
                        }
                    }
                    is Resource.Error -> {
                        // Handle error case
                    }
                    is Resource.Loading -> {
                        // Handle loading state
                    }
                }
            }
            .flowOn(ioDispatcher)
            .launchIn(viewModelScope)
    }

    fun togglePlayPause() {
        _exoPlayer?.let { player ->
            if (player.isPlaying) {
                player.pause()
                isPlaying = false
            } else {
                player.play()
                isPlaying = true
            }
        }
    }

    fun releasePlayer() {
        _exoPlayer?.run {
            playbackPosition = currentPosition
            playWhenReady = this.playWhenReady
            release()
        }
        _exoPlayer = null
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }
} 