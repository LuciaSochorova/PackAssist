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
import com.example.packassist.data.entitiesAndDaos.ItemsOfCollection
import com.example.packassist.data.repositories.CollectionsRepository
import com.example.packassist.data.repositories.ItemsRepository
import com.example.packassist.navigation.EVENT_ID_ARG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * A ViewModel class for the collection creation screen.
 *
 * @property collectionsRepository The repository for accessing collection data.
 * @property itemsRepository The repository for accessing item data.
 *
 * @param savedStateHandle The saved state handle for the ViewModel.
 */
class CollectionCreationViewModel(
    savedStateHandle: SavedStateHandle,
    private val collectionsRepository: CollectionsRepository,
    private val itemsRepository: ItemsRepository
) : ViewModel() {
    var state by mutableStateOf(CollectionCreationUiState())
        private set

    private val eventId: Int? = savedStateHandle.get<String>(EVENT_ID_ARG)?.toInt()
    private var _allCollectionsToImport: List<ItemsOfCollection> = listOf()

    init {
        viewModelScope.launch {
            _allCollectionsToImport =
                collectionsRepository.getAllNoEventCollectionsWithItems().first()
            state = state.copy(collectionsToImport = _allCollectionsToImport)
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
    }

    /**
     * Updates an existing item name.
     *
     * @param item The new item name.
     * @param index The index of the item to update.
     */
    fun onChangeExistingItem(item: String, index: Int) {
        val items = state.items.toMutableList()
        items[index] = item
        state = state.copy(items = items)
    }

    /**
     * Adds a new item to the collection.
     *
     */
    fun addItem() {
        if (state.newItem.isNotEmpty()) {
            val items = state.items.toMutableList()
            items.add(0, state.newItem)
            state = state.copy(items = items, newItem = "")
        }
        validate()
    }

    /**
     * Removes any blank items from the collection.
     *
     */
    fun ifEmptyDeleteItem() {
        val items = state.items.filter { it.isNotBlank() }
        state = state.copy(items = items)
        validate()
    }


    /**
     * Saves the collection to the database.
     *
     */
    fun saveCollection() {
        viewModelScope.launch(Dispatchers.IO) {
            if (state.isValid) {
                val rowId = collectionsRepository.insertCollection(
                    Collection(name = state.name, event = eventId)
                )

                val coll = collectionsRepository.getCollectionId(rowId)

                state = state.copy(items = state.items.sorted())
                state.items.forEach { name ->
                    if (name.isNotEmpty()) {
                        itemsRepository.upsertItem(itemToItem(name = name, collection = coll))
                    }
                }

            }
        }

    }

    /**
     * Filters the list of collections available to import based on the given query.
     *
     * @param query The query to filter the collections by.
     */
    fun filterImportCollections(query: String) {
        state = state.copy(collectionsToImport = _allCollectionsToImport.filter {
            it.collection.name.contains(
                query,
                ignoreCase = true
            )
        })
    }

    /**
     * Shows or hides the import dialog.
     *
     * @param bool True to show the dialog, false to hide it.
     */
    fun showImportDialog(bool: Boolean) {
        state = state.copy(showImportDialog = bool)
    }

    /**
     * Imports the items from the selected collection.
     *
     * @param index The index of the collection to import from.
     */
    fun importItemsFromCollection(index: Int) {
        val items = state.collectionsToImport[index].items
        state = state.copy(items = state.items + items.map { it.name })
        validate()
    }


    private fun validate() {
        state = state.copy(
            isValid =
            (state.items.isNotEmpty() && state.name.isNotEmpty())
        )
    }

    private fun itemToItem(name: String, collection: Int): Item = Item(
        name = name,
        collection = collection
    )


    /**
     * A companion object for the [CollectionCreationViewModel] class.
     *
     * Contains the [Factory] property, which is a [ViewModelProvider.Factory] that creates instances of [CollectionCreationViewModel].
     */
    companion object {
        val Factory = viewModelFactory {
            initializer {
                val collRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PackAssistApplication).container.collectionsRepository
                val itemRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PackAssistApplication).container.itemsRepository
                CollectionCreationViewModel(
                    savedStateHandle = this.createSavedStateHandle(),
                    collectionsRepository = collRepository,
                    itemsRepository = itemRepository
                )
            }
        }
    }
}


/**
 * A data class that represents the state of the collection creation screen.
 *
 * @property name The name of the collection.
 * @property newItem The name of the new item to be added.
 * @property items The list of items in the collection.
 * @property isValid True if the collection is valid, false otherwise.
 * @property collectionsToImport The list of collections to import items from.
 * @property showImportDialog True if the import dialog should be shown, false otherwise.
 * @constructor Create empty Collection creation ui state
 */
data class CollectionCreationUiState(
    val name: String = "",
    val newItem: String = "",
    val items: List<String> = listOf(),
    val isValid: Boolean = false,
    val collectionsToImport: List<ItemsOfCollection> = listOf(),
    val showImportDialog: Boolean = false
)


