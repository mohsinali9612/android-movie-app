package com.example.movie_app.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.movie_app.R


val appFonts = FontFamily(
    Font(R.font.inter_tight_regular),
    Font(R.font.inter_tight_bold),
    Font(R.font.inter_tight_light),
    Font(R.font.inter_tight_medium),
    Font(R.font.inter_tight_semi_bold),
    Font(R.font.inter_tight_extra_bold),
    Font(R.font.inter_tight_thin),
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.W500,
        fontFamily = appFonts,
        fontSize = 38.sp,
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.W500,
        fontFamily = appFonts,
        fontSize = 32.sp,
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.W500,
        fontFamily = appFonts,
        fontSize = 26.sp,
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.W500,
        fontFamily = appFonts,
        fontSize = 20.sp,
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = appFonts,
        fontSize = 18.sp,
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = appFonts,
        fontSize = 16.sp,
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = appFonts,
        fontSize = 14.sp,
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = appFonts,
        fontSize = 12.sp,
    ),
)
