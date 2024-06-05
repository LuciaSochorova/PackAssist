package com.example.packassist.ui.screens.events

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.packassist.PackAssistApplication
import com.example.packassist.data.entitiesAndDaos.ItemsOfCollection
import com.example.packassist.data.repositories.CollectionsRepository
import com.example.packassist.data.repositories.EventsRepository
import com.example.packassist.navigation.EVENT_ID_ARG
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ListOfEventCollectionsUiState(
    val eventId: Int = 0,
    val eventName: String = "",
    val collections: List<ItemsOfCollection> = listOf()
)

class EventCollectionsManagementViewModel(
    savedStateHandle: SavedStateHandle,
    private val collectionsRepository: CollectionsRepository,
    private val eventsRepository: EventsRepository
) : ViewModel() {
    private val eventId: Int = checkNotNull(savedStateHandle[EVENT_ID_ARG]).toString().toInt()
    private var eventName = ""
    init {
        viewModelScope.launch {
            eventName = eventsRepository.getEventName(eventId)
        }
    }

    val uiState: StateFlow<ListOfEventCollectionsUiState> =
        collectionsRepository.getEventCollectionsWithItemsStream(eventId).map {
            ListOfEventCollectionsUiState(eventId, eventName, it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ListOfEventCollectionsUiState()
        )


    companion object {
        val Factory = viewModelFactory {
            initializer {
                val collectionsRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PackAssistApplication).container.collectionsRepository
                val eventsRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PackAssistApplication).container.eventsRepository
                val savedStateHandle = createSavedStateHandle()
                EventCollectionsManagementViewModel(
                    savedStateHandle = savedStateHandle,
                    collectionsRepository = collectionsRepository,
                    eventsRepository = eventsRepository
                )
            }
        }
    }


}