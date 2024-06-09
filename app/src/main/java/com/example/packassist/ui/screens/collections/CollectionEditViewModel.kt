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
import com.example.packassist.ui.navigation.COLLECTION_ID_ARG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * A view model for the collection edit screen
 *
 * @property collectionsRepository
 * @property itemsRepository
 * @constructor
 *
 * @param savedStateHandle
 */
class CollectionEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val collectionsRepository: CollectionsRepository,
    private val itemsRepository: ItemsRepository,
) : ViewModel() {
    private val collectionId: Int =
        checkNotNull(savedStateHandle[COLLECTION_ID_ARG]).toString().toInt()


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
                    items = original.items.sortedBy { it.name },
                    isValid = true
                )

        }
    }


    /**
     * Updates the name of the collection.
     *
     * @param name The new name of the collection.
     */
    fun onNameChange(name: String) {
        state = state.copy(name = name)
        validate()
    }

    /**
     * Updates the new item name.
     *
     * @param newItem The new item name.
     */
    fun onNewItemChange(newItem: String) {
        state = state.copy(newItem = newItem)
        validate()
    }

    /**
     * Updates name of an existing item in the collection.
     *
     * @param item The new name of the item.
     * @param index The index of the item in the collection.
     */
    fun onChangeExistingItem(item: String, index: Int) {
        val items = state.items.toMutableList()
        items[index] = items[index].copy(name = item)
        state = state.copy(items = items)
    }

    /**
     * Adds a newItem to the collection.
     *
     */
    fun addItem() {
        if (state.newItem.isNotEmpty()) {
            val items = state.items.toMutableList()
            items.add(0, Item(id = 0, name = state.newItem, collection = collectionId))
            state = state.copy(items = items, newItem = "")
        }
        validate()
    }


    /**
     * Removes empty items from the collection.
     *
     */
    fun ifEmptyDeleteItem() {
        val items = state.items.filter { it.name.isNotBlank() }
        deleted.addAll(state.items.filter { it.name.isBlank() }.map { it.id })
        state = state.copy(items = items)
        validate()
    }

    /**
     * Saves the collection and its items to the database.
     *
     */
    fun saveCollection() {
        if (state.isValid) {
            viewModelScope.launch(Dispatchers.IO) {
                deleted.forEach {
                    itemsRepository.deleteItem(it)
                }
            }
            viewModelScope.launch(Dispatchers.IO) {
                var items = state.items.filter { it.name.isNotBlank() }
                if (state.newItem.isNotBlank()) {
                    items = items.plus(Item(name = state.newItem, collection = collectionId))
                }

                collectionsRepository.upsertItemsOfCollection(
                    collection = Collection(
                        id = collectionId,
                        name = state.name,
                        event = eventId
                    ),

                    items = items
                )
            }
        }
    }

    /**
     * Deletes the collection from the database.
     *
     */
    fun deleteCollection() {
        viewModelScope.launch(Dispatchers.IO) {
            collectionsRepository.deleteCollection(Collection(id = collectionId))
        }
    }

    private fun validate() {
        state = state.copy(
            isValid =
            (state.name.isNotEmpty() && (state.items.isNotEmpty() || state.newItem.isNotBlank()))
        )
    }

    /**
     * A companion object for the [CollectionEditViewModel] class.
     *
     * Contains the [Factory] property, which is a [ViewModelProvider.Factory] that creates instances of [CollectionEditViewModel].
     */
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

/**
 * Represents the UI state of the CollectionEdit screen.
 *
 * @property name The name of the collection.
 * @property newItem The name of the new item to be added.
 * @property items The list of items in the collection.
 * @property isValid Whether the collection is valid.
 * @constructor Create empty Collection edit ui state
 */
data class CollectionEditUiState(
    val name: String = "",
    val newItem: String = "",
    val items: List<Item> = listOf(),
    val isValid: Boolean = false
)