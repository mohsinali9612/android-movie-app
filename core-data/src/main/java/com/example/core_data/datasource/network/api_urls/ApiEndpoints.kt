package com.example.core_data.datasource.network.api_urls

object ApiEndpoints {
    // Server URLs
    private const val SERVER_URL = "https://api.themoviedb.org"
    private const val BASE_URL = "$SERVER_URL/3"
    const val SearchMulti = "$BASE_URL/search/multi"
    const val MovieVideo = "$BASE_URL/movie"
    const val TvVideo = "$BASE_URL/tv"
    const val Videos = "videos"

}