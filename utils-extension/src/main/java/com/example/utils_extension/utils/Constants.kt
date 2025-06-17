package com.example.utils_extension.utils

sealed interface ExtConstants {

    data object Config{
        const val MOVIE_API = "Movie-Api-Key"
        const val MOVIE_ACCESS_TOKEN = "Movie-Access-Token"

    }

    data object IntegerConstants{
        const val ZERO = 0
        const val ONE = 1
        const val TWO = 2

    }

    data object FloatConstants{
        const val ZERO = 0f
        const val ONE = 1f
    }


    data object AnimationConstants : ExtConstants {
        const val PAGE_ANIM_DURATION = 500
        const val IMAGE_ANIMATION_DURATION=800
    }

    data object StringConstants : ExtConstants {
        const val EMPTY = ""
        const val NO_IMAGE_URL = "https://img.freepik.com/premium-vector/image-available-icon-set-default-missing-photo-stock-vector-symbol-black-filled-outlined-style-no-image-found-sign_268104-6708.jpg?semt=ais_hybrid&w=740"
    }

    object NetworkConstants : ExtConstants {

        const val ERROR_OCCURRED = "Error Occurred"

        const val ERROR_400 = 400
        const val SESSION_EXPIRED_CODE = 401

    }

}