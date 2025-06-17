package com.example.movie_app.presentation.ui_utils.button

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.example.movie_app.presentation.theme.AppDimens

@Composable
internal fun Modifier.defaultFullWidthButtonModifier() = composed(factory = {
    this.then(
        Modifier
        .wrapContentSize()
        .height(AppDimens.Button.defaultButtonHeight))
})