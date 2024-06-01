package com.example.packassist.ui.screens.collections

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.packassist.PackAssistApplication
import com.example.packassist.data.entitiesAndDaos.Collection
import com.example.packassist.data.entitiesAndDaos.Item
import com.example.packassist.data.repositories.CollectionsRepository
import com.example.packassist.data.repositories.ItemsRepository
import com.example.packassist.navigation.collectionIdArg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CollectionEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val collectionsRepository: CollectionsRepository,
    private val itemsRepository: ItemsRepository,
) : ViewModel() {
    private val collectionId: Int =
        checkNotNull(savedStateHandle[collectionIdArg]).toString().toInt()


    private val deleted = mutableListOf<Int>()
    private var eventId: Int? = null
    var state by mutableStateOf(CollectionEditUiState())
        private set

    init {
        viewModelScope.launch {
            eventId = collectionsRepository.getCollectionEvent(collectionId)
        }
        viewModelScope.launch {
            val original = collectionsRepository.getItemsOfCollection(collectionId)
            state =
                CollectionEditUiState(
                    name = original.collection.name,
                    items = if (original.items.isEmpty()) emptyList() else original.items.map {
                        Pair(it.id, it.name)
                    },
                    isValid = true
                )

        }
    }


    fun onNameChange(name: String) {
        state = state.copy(name = name)
        validate()
    }

    fun onNewItemChange(newItem: String) {
        state = state.copy(newItem = newItem)
    }

    fun onChangeExistingItem(item: String, index: Int) {
        val items = state.items.toMutableList()
        items[index] = items[index].copy(second = item)
        state = state.copy(items = items)
    }

    fun addItem() {
        if (state.newItem.isNotEmpty()) {
            val items = state.items.toMutableList()
            items.add(0, Pair(0, state.newItem))
            state = state.copy(items = items, newItem = "")
        }
        validate()
    }


    fun ifEmptyDeleteItem(index: Int) {
        val item = state.items[index]
        if (item.second.isBlank()) {
            if (item.first != 0) {
                deleted.add(state.items[index].first)
            }
            val items = state.items.toMutableList()
            items.removeAt(index)
            state = state.copy(items = items)
        }
        validate()
    }

    fun saveCollection() {
        if (state.isValid) {
            viewModelScope.launch(Dispatchers.IO) {
                deleted.forEach {
                    itemsRepository.deleteItem(it)
                }
            }
            viewModelScope.launch(Dispatchers.IO) {
                collectionsRepository.upsertItemsOfCollection(
                    collection = Collection(
                        id = collectionId,
                        name = state.name,
                        event = eventId
                    ),
                    items = state.items.map {
                        Item(
                            id = it.first,
                            name = it.second,
                            collection = collectionId
                        )
                    }
                )
            }
        }
    }

    fun deleteCollection() {
        viewModelScope.launch(Dispatchers.IO) {
            collectionsRepository.deleteCollection(Collection(id = collectionId))
        }
    }

    private fun validate() {
        state = state.copy(
            isValid =
            (state.name.isNotEmpty() && state.items.isNotEmpty())
        )
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val collRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PackAssistApplication).container.collectionsRepository
                val itemRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PackAssistApplication).container.itemsRepository
                val savedStateHandle = createSavedStateHandle()
                CollectionEditViewModel(
                    collectionsRepository = collRepository,
                    itemsRepository = itemRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }

}

data class CollectionEditUiState(
    val name: String = "",
    val newItem: String = "",
    val items: List<Pair<Int, String>> = listOf(),
    val isValid: Boolean = false
)