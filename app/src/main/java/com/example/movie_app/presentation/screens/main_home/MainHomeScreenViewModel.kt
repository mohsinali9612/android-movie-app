package com.example.movie_app.presentation.screens.main_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_data.datasource.network.result_state.Resource
import com.example.core_data.domain.models.dto.MovieTypeDTO
import com.example.core_data.domain.models.dto.SearchMultiDTO
import com.example.core_data.domain.use_case.SearchMultiUseCase
import com.example.utils_extension.events.BaseUiEvents
import com.example.utils_extension.events.Events
import com.example.utils_extension.utils.ExtConstants.IntegerConstants.ONE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainHomeScreenViewModel(
    private val ioDispatcher : CoroutineDispatcher,
    private val searchMultiUseCase: SearchMultiUseCase
): ViewModel() {
    private val _state = MutableStateFlow(MainHomeScreenUiState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 500) ,
        initialValue = MainHomeScreenUiState()
    )

    private val _eventFlow = Channel<MainHomeScreenUiEvents>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private fun emitUIEvents(event: MainHomeScreenUiEvents){
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }

    private fun searchMulti(page:Int= ONE){
        searchMultiUseCase.invoke(_state.value.searchText, page = page)
            .onEach {result->
                when(result){
                    is Resource.Success->{
                        val responseResultData = result.data
                        updateListState(responseResultData = responseResultData)
                    }
                    is Resource.Error->{
                        Events.updateBaseEvent(BaseUiEvents.ShowToast(message = result.message))
                    }
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = result.isLoading) }
                    }
                }
            }
            .flowOn(ioDispatcher)
            .launchIn(viewModelScope)
    }

    private fun updateListState(responseResultData: SearchMultiDTO?) {
        _state.update { currentState ->
            val previousList = currentState.searchResponse?.result ?: emptyList()
            val newList = responseResultData?.result ?: emptyList()


            val combinedMap = mutableMapOf<String, MovieTypeDTO>()


            previousList.forEach { oldItem ->
                combinedMap[oldItem.mediaType] = oldItem
            }


            newList.forEach { newItem ->
                val existing = combinedMap[newItem.mediaType]
                if (existing != null) {

                    val existingIds = existing.mediaItems.map { it.id }.toSet()
                    

                    val uniqueNewItems = newItem.mediaItems.filter { it.id !in existingIds }
                    

                    combinedMap[newItem.mediaType] = existing.copy(
                        mediaItems = existing.mediaItems + uniqueNewItems
                    )
                } else {
                    combinedMap[newItem.mediaType] = newItem
                }
            }

            currentState.copy(
                searchResponse = SearchMultiDTO(
                    result = combinedMap.values.toList(),
                    metaData = responseResultData?.metaData
                ),
                isLoading = false
            )
        }
    }


    fun onIntent(intent: MainHomeScreenUiIntents){
        when(intent){
            is MainHomeScreenUiIntents.TextFieldsIntent.OnSearchTextUpdate->{
                _state.update { it.copy(searchText = intent.search) }
            }
            is MainHomeScreenUiIntents.ButtonIntents.OnSearchButtonIntent->{
                _state.value.apply {
                    _state.update {currentState->
                        currentState.copy(
                            searchResponse = SearchMultiDTO(
                                result = searchResponse?.result ?: emptyList(),
                                metaData = searchResponse?.metaData
                            ),
                            isLoading = false
                        )

                    }
                }

                searchMulti()
            }
            is MainHomeScreenUiIntents.ListItemIntent.OnMovieItemClick->{
                emitUIEvents(MainHomeScreenUiEvents.Navigate.MoveToDetail(intent.media))
            }
            is MainHomeScreenUiIntents.ListItemIntent.OnTriggerPagination->{
                _state.value.searchResponse?.metaData?.apply {
                    if (page==totalPages) return
                    searchMulti(page = page?.plus(ONE) ?: ONE)
                }

            }
            else->Unit
        }
    }

}