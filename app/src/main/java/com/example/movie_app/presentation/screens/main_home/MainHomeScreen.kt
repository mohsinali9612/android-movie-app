package com.example.movie_app.presentation.screens.main_home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.movie_app.R
import com.example.movie_app.presentation.navigation.AppRoutes
import com.example.movie_app.presentation.theme.AppColors
import com.example.movie_app.presentation.theme.AppDimens
import com.example.movie_app.presentation.ui_utils.button.PrimaryButton
import com.example.movie_app.presentation.ui_utils.list_items.MovieCarousal
import com.example.movie_app.presentation.ui_utils.text.CustomTextToDisplay
import com.example.movie_app.presentation.ui_utils.textfield.SearchBar
import com.example.utils_extension.compose_ui_utils.ConsumeUIEffects
import com.example.utils_extension.navigation.safeNavToNextScreen
import com.example.utils_extension.safe_click.clickableSingleWithoutRipple
import com.example.utils_extension.utils.ExtConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainHomeScreen(
    navController: NavHostController,
    dataState: StateFlow<MainHomeScreenUiState>,
    uiEvents: Flow<MainHomeScreenUiEvents>,
    onIntent: (MainHomeScreenUiIntents) -> Unit,
) {
    val state by dataState.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    ConsumeUIEffects(uiEvents) { event, scope ->
        when (event) {
            is MainHomeScreenUiEvents.Navigate.MoveToDetail -> {
                navController.safeNavToNextScreen(AppRoutes.Detail(event.media), shouldClearBackStack = false)
            }
            else -> Unit
        }
    }

    MainContent(state, onIntent = {
        if (it !is MainHomeScreenUiIntents.TextFieldsIntent.OnSearchTextUpdate) {
            keyboardController?.hide()
            focusManager.clearFocus()
        }
        onIntent(it)
    })

}

@Composable
private fun MainContent(
    dataState: MainHomeScreenUiState,
    onIntent: (MainHomeScreenUiIntents) -> Unit
){
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var isKeyboardVisible by remember { mutableStateOf(false) }

    val keyboardHeight = WindowInsets.ime.getBottom(density = LocalDensity.current)

    LaunchedEffect(key1 = keyboardHeight) {
        isKeyboardVisible = keyboardHeight > 0
    }

    Scaffold(
        modifier = Modifier
            .background(AppColors.mainBackgroundColor)
            .fillMaxSize()
            .statusBarsPadding()
            .imePadding()
            .clickableSingleWithoutRipple {
                focusManager.clearFocus(force = true)
                keyboardController?.hide()
            }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center
        ) {
            ConstraintLayout(
                Modifier
                    .fillMaxSize()
                    .background(AppColors.mainBackgroundColor)
                    .padding(horizontal = AppDimens.Padding.defaultPadding)
                    .padding(paddingValues)
            ) {
                val (searchBar, searchButton, listView,loader) = createRefs()

                SearchBar(
                    modifier = Modifier.constrainAs(searchBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(searchButton.start, margin = AppDimens.Padding.smallPadding)
                        width = Dimension.fillToConstraints
                    },
                    text = dataState.searchText,
                    keyboardActions = KeyboardActions(onSearch = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        onIntent(MainHomeScreenUiIntents.ButtonIntents.OnSearchButtonIntent)
                    })
                ) {
                    onIntent(MainHomeScreenUiIntents.TextFieldsIntent.OnSearchTextUpdate(search = it))
                }

                PrimaryButton(
                    modifier = Modifier.constrainAs(searchButton) {
                        end.linkTo(parent.end)
                    },
                    buttonText = CustomTextToDisplay.StringResourceText(text = R.string.search),
                ) {
                    onIntent(MainHomeScreenUiIntents.ButtonIntents.OnSearchButtonIntent)
                }



                LazyColumn(
                    modifier = Modifier
                        .imePadding()
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                        .constrainAs(listView) {
                            linkTo(
                                top = searchBar.bottom,
                                bottom = parent.bottom,
                                topMargin = AppDimens.Padding.mediumPadding
                            )
                            linkTo(start = parent.start, end = parent.end)
                            height = Dimension.fillToConstraints
                            width = Dimension.matchParent
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppDimens.Padding.smallPadding)
                ) {
                    itemsIndexed(items = dataState.searchResponse?.result?:emptyList()){index, item ->
                        MovieCarousal(
                            modifier = Modifier.fillMaxWidth(),
                            data = item,
                            onLoadMore = {
                                onIntent(MainHomeScreenUiIntents.ListItemIntent.OnTriggerPagination)
                            },
                            onClick = {
                                onIntent(MainHomeScreenUiIntents.ListItemIntent.OnMovieItemClick(media = it))
                            }
                        )
                    }
                }

                if (dataState.isLoading
                    &&
                    (dataState.searchResponse?.metaData?.page ?: ExtConstants.IntegerConstants.ONE) <= ExtConstants.IntegerConstants.ONE
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .constrainAs(loader) {
                                linkTo(start = parent.start, end = parent.end)
                                linkTo(top = parent.top, bottom = parent.bottom)
                            }
                            .size(AppDimens.Icons.loaderSize),
                        color = AppColors.secondaryColor
                    )
                }
            }
        }

    }
}

