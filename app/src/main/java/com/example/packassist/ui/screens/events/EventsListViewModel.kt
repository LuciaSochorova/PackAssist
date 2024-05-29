package com.example.packassist.ui.screens.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.packassist.PackAssistApplication
import com.example.packassist.data.entitiesAndDaos.Event
import com.example.packassist.data.repositories.EventsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ListOfEventsUiState(
    val listOfEvents: List<Event> = listOf()
)


class EventsListViewModel(
    private val eventsRepository: EventsRepository
) : ViewModel() {
    val uiState: StateFlow<ListOfEventsUiState> =
        eventsRepository.getAllEventsStream().map { ListOfEventsUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ListOfEventsUiState()
            )


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