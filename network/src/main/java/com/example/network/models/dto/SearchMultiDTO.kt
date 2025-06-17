fun SearchMultiResponse.toMovieTypeDTO(): List<MovieTypeDTO> {
    return results
        .groupBy { it.mediaType }
        .map { (mediaType, items) ->
            MovieTypeDTO(
                mediaType = mediaType ?: "unknown",
                mediaItems = items.sortedBy { item ->
                    item.title ?: item.originalTitle ?: item.name ?: item.originalName ?: ""
                }
            )
        }
} 