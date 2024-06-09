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
import com.example.packassist.ui.navigation.EVENT_ID_ARG
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * The UI state for the ManageEventCollectionsScreen.
 *
 * @property eventId The ID of the event.
 * @property eventName The name of the event.
 * @property collections The list of collections associated with the event.
 * @constructor Create empty List of event collections ui state
 */
data class ListOfEventCollectionsUiState(
    val eventId: Int = 0,
    val eventName: String = "",
    val collections: List<ItemsOfCollection> = listOf()
)


/**
 * A view model for the event collections management screen
 *
 * @property collectionsRepository The repository for accessing collection data.
 * @property eventsRepository The repository for accessing event data.
 *
 * @param savedStateHandle The saved state handle for the ViewModel.
 */
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

    /**
     * The UI state of the screen.
     */
    val uiState: StateFlow<ListOfEventCollectionsUiState> =
        collectionsRepository.getEventCollectionsWithItemsStream(eventId).map {
            ListOfEventCollectionsUiState(eventId, eventName, it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ListOfEventCollectionsUiState()
        )


    /**
     * A companion object for the [EventCollectionsManagementViewModel] class.
     *
     * Contains the [Factory] property, which is a [ViewModelProvider.Factory] that creates instances of [EventCollectionsManagementViewModel].
     */
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