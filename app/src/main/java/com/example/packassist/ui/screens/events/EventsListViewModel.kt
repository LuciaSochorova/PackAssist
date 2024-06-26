package com.example.packassist.ui.screens.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.packassist.PackAssistApplication
import com.example.packassist.data.entitiesAndDaos.Event
import com.example.packassist.data.repositories.EventsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * List of events ui state
 *
 * @property listOfEvents
 * @constructor Create empty List of events ui state
 */
data class ListOfEventsUiState(
    val listOfEvents: List<Event> = listOf()
)


/**
 * ViewModel for the EventsList screen.
 *
 * @property eventsRepository The repository for accessing event data.
 */
class EventsListViewModel(
    private val eventsRepository: EventsRepository
) : ViewModel() {
    private val _filterQuery = MutableStateFlow("")
    private val _uiState =
        eventsRepository.getAllEventsOrderedStream().map { ListOfEventsUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = ListOfEventsUiState()
            )

    /**
     * The UI state of the EventsList screen.
     */
    val uiState: StateFlow<ListOfEventsUiState> = combine(_uiState, _filterQuery)
    { uiState, filterQuery ->
        ListOfEventsUiState(uiState.listOfEvents.filter {
            it.name.contains(
                filterQuery,
                ignoreCase = true
            ) || it.location?.contains(filterQuery, ignoreCase = true) ?: false
        })
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ListOfEventsUiState()
        )


    /**
     * Filters the events based on the given query.
     *
     * @param query The filter query.
     */
    fun filterEvents(query: String) {
        _filterQuery.update { query }
    }


    /**
     * A companion object for the [EventsListViewModel] class.
     *
     * Contains the [Factory] property, which is a [ViewModelProvider.Factory] that creates instances of [EventsListViewModel].
     */
    companion object {
        val Factory = viewModelFactory {
            initializer {
                val eventsRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PackAssistApplication).container.eventsRepository
                EventsListViewModel(
                    eventsRepository = eventsRepository,
                )
            }
        }
    }

}