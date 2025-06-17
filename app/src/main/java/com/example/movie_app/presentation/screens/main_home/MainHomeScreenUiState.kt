package com.example.movie_app.presentation.screens.main_home

import com.example.core_data.domain.models.dto.SearchMultiDTO
import com.example.utils_extension.utils.ExtConstants


data class MainHomeScreenUiState(
    val searchText:String= ExtConstants.StringConstants.EMPTY,
    val searchResponse: SearchMultiDTO?=null,
    val isLoading: Boolean=false
    )