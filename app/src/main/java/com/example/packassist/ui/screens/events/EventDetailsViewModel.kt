package com.example.packassist.ui.screens.events

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.packassist.PackAssistApplication
import com.example.packassist.data.entitiesAndDaos.Event
import com.example.packassist.data.entitiesAndDaos.Item
import com.example.packassist.data.repositories.CollectionsRepository
import com.example.packassist.data.repositories.EventsRepository
import com.example.packassist.data.repositories.ItemsRepository
import com.example.packassist.navigation.EVENT_ID_ARG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date


data class EventDetailsUiState(
    val event: EventState = EventState(),
    val collections: List<Pair<CollectionItems, Boolean>> = listOf()
)

data class EventState(
    val id: Int = 0,
    val name: String = "",
    val location: String? = null,
    val date: Date? = null,
    val notes: String? = null,
    val writingNotes: Boolean = false,
    val pickingDate: Boolean = false
)

data class CollectionItems(
    val collectionId: Int = 0,
    val name: String = "",
    val items: List<Item> = listOf()
)


class EventDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val eventsRepository: EventsRepository,
    private val itemsRepository: ItemsRepository,
    private val collectionsRepository: CollectionsRepository
) : ViewModel() {
    private val eventId: Int =
        checkNotNull(savedStateHandle[EVENT_ID_ARG]).toString().toInt()

    private val _eventState = MutableStateFlow(EventState())

    private val _expanded = MutableStateFlow(mapOf<Int, Boolean>())


    private val _collItems: StateFlow<List<CollectionItems>> =
        collectionsRepository.getEventCollectionsWithItemsStream(eventId).map { list ->
            list.map {
                CollectionItems(
                    collectionId = it.collection.id,
                    name = it.collection.name,
                    items = it.items.sortedBy { item -> item.name }
                )
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())


    init {
        viewModelScope.launch {
            val original = eventsRepository.getEventCollections(eventId)
            _eventState.update {
                it.copy(
                    id = eventId,
                    name = original.event.name,
                    location = original.event.location,
                    date = original.event.date,
                    notes = original.event.notes
                )
            }

            original.collections.forEach { coll ->
                _expanded.update {
                    it + (coll.id to !itemsRepository.getItemsOfCollection(coll.id)
                        .all { item -> item.packed })
                }

            }

        }

    }

    val state = combine(
        _eventState,
        _collItems,
        _expanded
    ) { event, collections, expanded ->

        EventDetailsUiState(
            event = event,
            collections = collections.map { it to (expanded[it.collectionId] ?: true) }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EventDetailsUiState())

    fun saveEventInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            eventsRepository.updateEvent(
                Event(
                    eventId,
                    name = _eventState.value.name,
                    location = _eventState.value.location,
                    date = _eventState.value.date,
                    notes = _eventState.value.notes
                )
            )
        }
    }

    fun deleteEvent() {
        viewModelScope.launch(Dispatchers.IO) {
            eventsRepository.deleteEvent(Event(id = eventId, name = _eventState.value.name))
        }
    }

    fun changeName(newName: String) {
        _eventState.update {
            it.copy(name = newName)
        }
    }

    fun writeNotes(truth: Boolean) {
        _eventState.update { it.copy(writingNotes = truth) }
    }

    fun changeNotes(newNotes: String) {
        _eventState.update {
            if (newNotes.isBlank()) it.copy(notes = null) else it.copy(notes = newNotes)
        }
    }

    fun changeLocation(newLocation: String) {
        _eventState.update {
            if (newLocation.isBlank()) it.copy(location = null) else it.copy(location = newLocation)
        }
    }

    fun packItem(item: Item) {
        viewModelScope.launch {
            itemsRepository.updateItem(item.copy(packed = !item.packed))
        }
    }

    fun changeDate(newDate: Long?) {
        _eventState.update {
            it.copy(
                date = if (newDate != null) Date(newDate) else null
            )
        }
    }

    fun pickDate(truth: Boolean) {
        _eventState.update {
            it.copy(pickingDate = truth)
        }
    }

    fun changeExpandCollection(collectionId: Int) {
        _expanded.update {
            val mutable = it.toMutableMap()
            it[collectionId]?.let { mutable.replace(collectionId, !mutable[collectionId]!!) }
                ?: mutable.put(collectionId, false)
            mutable.toMap()
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val eventsRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PackAssistApplication).container.eventsRepository
                val itemsRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PackAssistApplication).container.itemsRepository
                val collectionsRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PackAssistApplication).container.collectionsRepository
                val savedStateHandle = createSavedStateHandle()
                EventDetailsViewModel(
                    eventsRepository = eventsRepository,
                    savedStateHandle = savedStateHandle,
                    itemsRepository = itemsRepository,
                    collectionsRepository = collectionsRepository
                )
            }
        }
    }

}

