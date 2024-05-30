package com.example.packassist.ui.screens.events

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.packassist.PackAssistApplication
import com.example.packassist.data.entitiesAndDaos.Event
import com.example.packassist.data.repositories.EventsRepository


class EventCreationViewModel(
    private val eventsRepository: EventsRepository
) : ViewModel() {
    var state = mutableStateOf("")
        private set

    fun changeName(name: String) {
        state.value = name
    }

    suspend fun saveEvent(): Int {
        val rowId = eventsRepository.insertEvent(
            Event(
                name = state.value,
                date = null,
                notes = null,
                location = null
            )
        )

        return eventsRepository.getEventId(rowId)
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val eventsRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PackAssistApplication).container.eventsRepository
                EventCreationViewModel(
                    eventsRepository = eventsRepository,
                )
            }
        }
    }
}