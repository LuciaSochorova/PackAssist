package com.example.packassist.ui.screens.events

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.packassist.PackAssistApplication
import com.example.packassist.data.entitiesAndDaos.Event
import com.example.packassist.data.repositories.EventsRepository


/**
 * A view model for the event creation screen (dialog)
 *
 * @property eventsRepository The repository for accessing event data.
 * @constructor Create empty Event creation view model
 */
class EventCreationViewModel(
    private val eventsRepository: EventsRepository
) : ViewModel() {
    /**
     * The current ui state of the event creation.
     */
    var state = mutableStateOf("")
        private set

    /**
     * Changes the name of the event.
     *
     * @param name The new name of the event.
     */
    fun changeName(name: String) {
        state.value = name
    }

    /**
     * Saves the event to the database.
     *
     * @return The ID of the newly created event.
     */
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

    /**
     * A companion object for the [EventCreationViewModel] class.
     *
     * Contains the [Factory] property, which is a [ViewModelProvider.Factory] that creates instances of [EventCreationViewModel].
     */
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