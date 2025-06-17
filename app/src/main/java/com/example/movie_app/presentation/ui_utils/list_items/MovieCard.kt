package com.example.movie_app.presentation.ui_utils.list_items

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.example.core_data.domain.models.response_models.media.MediaDataModel
import com.example.movie_app.presentation.theme.AppColors
import com.example.movie_app.presentation.theme.AppDimens
import com.example.utils_extension.compose_ui_utils.animation.PulseAnimation
import com.example.movie_app.presentation.ui_utils.text.CustomText
import com.example.movie_app.presentation.ui_utils.text.CustomTextToDisplay
import com.example.utils_extension.utils.ExtConstants
import com.example.utils_extension.utils.ExtConstants.AnimationConstants.IMAGE_ANIMATION_DURATION
import com.example.utils_extension.utils.ExtConstants.IntegerConstants.ONE
import com.example.utils_extension.utils.ExtConstants.StringConstants.NO_IMAGE_URL

@Composable
fun MovieCard(modifier: Modifier, data: MediaDataModel) {
    Column(
        modifier = modifier
            .width(140.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MoviePoster(url = data.posterPath)
        CustomText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppDimens.Padding.defaultPadding),
            text = CustomTextToDisplay.StringText(
                text = data.name ?: data.title ?: ExtConstants.StringConstants.EMPTY
            ),
            color = AppColors.secondaryTextColor,
            fontSize = AppDimens.Fonts.font18,
            maxLines = 1,
            minLines = 1,
            textOverflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.W400
        )
    }
}

@Composable
fun MoviePoster(modifier: Modifier= Modifier, url: String?) {
    Box(
        modifier = modifier
            .height(220.dp)
            .clip(RoundedCornerShape(AppDimens.Radius.radius16)),
        contentAlignment = Alignment.Center
    ) {
        var imageLoadResult by remember {
            mutableStateOf<Result<Painter>?>(null)
        }
        var imageUrl = if (url != null) "https://image.tmdb.org/t/p/w500${url}" else NO_IMAGE_URL
        val painter = rememberAsyncImagePainter(
            model = imageUrl,
            onSuccess = {
                imageLoadResult =
                    if (it.painter.intrinsicSize.width > ONE && it.painter.intrinsicSize.height > ONE) {
                        Result.success(it.painter)
                    } else {
                        Result.failure(Exception("Invalid image size"))
                    }
            },
            onError = {
                it.result.throwable.printStackTrace()
                imageLoadResult = Result.failure(it.result.throwable)
            }
        )

        val painterState by painter.state.collectAsStateWithLifecycle()
        val transition by animateFloatAsState(
            targetValue = if (painterState is AsyncImagePainter.State.Success) {
                ExtConstants.FloatConstants.ONE
            } else {
                ExtConstants.FloatConstants.ZERO
            },
            animationSpec = tween(durationMillis = IMAGE_ANIMATION_DURATION)
        )

        when (val result = imageLoadResult) {
            null -> PulseAnimation(
                modifier = Modifier.size(60.dp)
            )

            else -> {
                Image(
                    painter = painter,
                    contentDescription = "",
                    contentScale = if (result.isSuccess) {
                        ContentScale.Crop
                    } else {
                        ContentScale.Fit
                    },
                    modifier = Modifier
                        .aspectRatio(
                            ratio = 0.65f,
                            matchHeightConstraintsFirst = true
                        )
                        .graphicsLayer {
                            rotationX = (1f - transition) * 30f
                            val scale = 0.8f + (0.2f * transition)
                            scaleX = scale
                            scaleY = scale
                        }
                )
            }
        }
    }
}