package com.example.movie_app.presentation.screens.detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.example.core_data.domain.models.response_models.media.MediaDataModel
import com.example.core_data.datasource.utils.isVideoContent
import com.example.movie_app.R
import com.example.movie_app.presentation.navigation.AppRoutes.VideoPlayer
import com.example.movie_app.presentation.theme.AppColors
import com.example.movie_app.presentation.theme.AppDimens
import com.example.utils_extension.compose_ui_utils.animation.PulseAnimation
import com.example.movie_app.presentation.ui_utils.button.PrimaryButton
import com.example.movie_app.presentation.ui_utils.list_items.MoviePoster
import com.example.movie_app.presentation.ui_utils.text.CustomText
import com.example.movie_app.presentation.ui_utils.text.CustomTextToDisplay
import com.example.utils_extension.navigation.safeNavToNextScreen
import com.example.utils_extension.utils.ExtConstants
import com.example.utils_extension.utils.ExtConstants.AnimationConstants.IMAGE_ANIMATION_DURATION
import com.example.utils_extension.utils.ExtConstants.StringConstants.NO_IMAGE_URL

@Composable
fun DetailScreen(
    navController: NavHostController,
    mediaDataModel: MediaDataModel
){
    Surface(
        modifier = Modifier
            .background(AppColors.mainBackgroundColor)
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .background(AppColors.mainBackgroundColor)
        ) {
            val (backBtn,banner,gradient,poster,button,title,description) = createRefs()

            IconButton(
                modifier = Modifier.constrainAs(backBtn) {
                    top.linkTo(parent.top, margin = AppDimens.Padding.mediumPadding)
                    start.linkTo(parent.start, margin = AppDimens.Padding.mediumPadding)
                },
                onClick = {},
            ) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = ExtConstants.StringConstants.EMPTY)
            }

            BannerImage(
                url = mediaDataModel.backdropPath,
                posterUrl = mediaDataModel.posterPath,
                modifier = Modifier.constrainAs(banner) {
                    linkTo(start = parent.start,end=parent.end)
                    linkTo(top = parent.top,bottom=parent.bottom)
                },
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.6f),
                                Color.Black.copy(0.8f)
                            )
                        )
                    )
                    .constrainAs(gradient) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(top = parent.top, bottom = parent.bottom)
                    }
            )

            MoviePoster(
                modifier = Modifier.constrainAs(poster) {
                    linkTo(start = parent.start,end=parent.end)
                    top.linkTo(parent.top, margin = AppDimens.Padding.xLargePadding)
                },
                url = mediaDataModel.posterPath,
            )

            CustomText(
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(title) {
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                            startMargin = AppDimens.Padding.mediumPadding,
                            endMargin = AppDimens.Padding.mediumPadding
                        )
                        top.linkTo(poster.bottom, margin = AppDimens.Padding.mediumPadding)
                    },
                text = CustomTextToDisplay.StringText(mediaDataModel.title ?: mediaDataModel.name ?: ""),
                color = AppColors.primaryWhiteTextColor,
                fontSize = AppDimens.Fonts.font24,
                fontWeight = FontWeight.Bold
            )

            CustomText(
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(description) {
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                            bias = ExtConstants.FloatConstants.ZERO,
                            startMargin = AppDimens.Padding.mediumPadding,
                            endMargin = AppDimens.Padding.mediumPadding
                        )
                        top.linkTo(title.bottom, margin = AppDimens.Padding.smallPadding12)
                    },
                text = CustomTextToDisplay.StringText(mediaDataModel.overview ?: ""),
                color = AppColors.primaryWhiteTextColor,
                fontSize = AppDimens.Fonts.font16,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )

            if (mediaDataModel.mediaType?.isVideoContent()==true){
                PrimaryButton(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .constrainAs(button) {
                            linkTo(start = parent.start, end = parent.end)
                            bottom.linkTo(parent.bottom, margin = AppDimens.Padding.smallPadding)
                        },
                    leadingIcon = {
                        Icon(Icons.Rounded.PlayArrow, contentDescription = ExtConstants.StringConstants.EMPTY)
                    },
                    buttonColor = AppColors.secondaryColor,
                    buttonText = CustomTextToDisplay.StringResourceText(R.string.play_video))
                {
                    mediaDataModel.apply {
                        navController.safeNavToNextScreen(
                            VideoPlayer(
                                id = id ?: ExtConstants.IntegerConstants.ZERO,
                                mediaType = mediaType ?:  ExtConstants.StringConstants.EMPTY
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BannerImage(modifier: Modifier,url: String?,posterUrl: String?){
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var imageLoadResult by remember {
            mutableStateOf<Result<Painter>?>(null)
        }
        val imageUrl = if (url!=null) "https://image.tmdb.org/t/p/original${url}" else if(posterUrl!=null) "https://image.tmdb.org/t/p/w500${posterUrl}"  else NO_IMAGE_URL
        val painter = rememberAsyncImagePainter(
            model = imageUrl,
            contentScale = ContentScale.FillHeight,
            onSuccess = {
                imageLoadResult =
                    if (it.painter.intrinsicSize.width > ExtConstants.IntegerConstants.ONE && it.painter.intrinsicSize.height > ExtConstants.IntegerConstants.ONE) {
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
                    contentDescription = ExtConstants.StringConstants.EMPTY,
                    contentScale = if (result.isSuccess) {
                        ContentScale.Crop
                    } else {
                        ContentScale.Fit
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = transition
                        }
                        .blur(radius = AppDimens.Radius.radius12)
                )
            }
        }
    }
}