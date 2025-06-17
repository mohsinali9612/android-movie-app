package com.example.movie_app.presentation.ui_utils.list_items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.core_data.domain.models.dto.MovieTypeDTO
import com.example.core_data.domain.models.response_models.media.MediaDataModel
import com.example.movie_app.presentation.theme.AppDimens
import com.example.movie_app.presentation.ui_utils.text.CustomText
import com.example.movie_app.presentation.ui_utils.text.CustomTextToDisplay
import com.example.utils_extension.safe_click.clickableWithoutRipple
import com.example.utils_extension.utils.ExtConstants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun MovieCarousal(
    modifier: Modifier,
    data: MovieTypeDTO,
    onClick: (MediaDataModel) -> Unit,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .filter { it }
            .flatMapLatest {
                snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            }
            .collect { visibleItems ->
                val lastVisible = visibleItems.lastOrNull()?.index ?: return@collect
                if (lastVisible >= data.mediaItems.size - ExtConstants.IntegerConstants.ONE) {
                    onLoadMore()
                }
            }
    }

    Column(modifier = modifier.wrapContentSize()) {
        CustomText(
            text = CustomTextToDisplay.StringText(data.mediaType.uppercase()),
            fontSize = AppDimens.Fonts.font24,
            fontWeight = FontWeight.W500,
            modifier = Modifier.wrapContentSize()
        )
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppDimens.Padding.smallPadding),
            horizontalArrangement = Arrangement.spacedBy(AppDimens.Padding.smallPadding)
        ) {
            items(
                items = data.mediaItems,
                key = { item ->
                    "${item.id}_${item.mediaType}_${item.posterPath ?: ""}_${item.backdropPath ?: ""}"
                }
            ) { item ->
                MovieCard(
                    modifier = Modifier
                        .wrapContentSize()
                        .clickableWithoutRipple {
                            onClick(item)
                        },
                    data = item
                )
            }
        }
    }
}

