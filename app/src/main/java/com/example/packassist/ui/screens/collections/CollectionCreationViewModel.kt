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
import com.example.packassist.navigation.EventIdArg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectionCreationViewModel (
    savedStateHandle: SavedStateHandle,
    private val collectionsRepository: CollectionsRepository,
    private val itemsRepository: ItemsRepository
) : ViewModel() {
    var state by mutableStateOf(CollectionUiState())
        private set

    private val eventId: Int? = savedStateHandle.get<String>(EventIdArg)?.toInt()
    //private val eventId: Int = savedStateHandle[EventIdArg]?.toInt() ?: 0
    fun onNameChange(name: String) {
        state = state.copy(name = name)
        validate()
    }

    fun onNewItemChange(newItem: String) {
        state = state.copy(newItem = newItem)
    }

    fun onChangeExistingItem(item: String, index: Int) {
        val items = state.items.toMutableList()
        items[index] = item
        state = state.copy(items = items)
    }

    fun addItem() {
        if (state.newItem.isNotEmpty()) {
            val items = state.items.toMutableList()
            items.add(0, state.newItem)
            state = state.copy(items = items, newItem = "")
        }
        validate()
    }

    fun ifEmptyDeleteItem(index: Int) {
        if (state.items[index].isEmpty()) {
            val items = state.items.toMutableList()
            items.removeAt(index)
            state = state.copy(items = items)
        }
        validate()
    }

    fun saveCollection() {
        viewModelScope.launch(Dispatchers.IO) {
            if (state.isValid) {
                val rowId = collectionsRepository.insertCollection(
                    Collection(name = state.name, event = eventId)
                )

                val coll = collectionsRepository.getCollectionId(rowId)

                state.items.forEach { name ->
                    if  (name.isNotEmpty() ) {
                    itemsRepository.upsertItem(itemToItem(name = name, collection = coll))
                    }
                }

            }
        }

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

    /*TODO IMPORT*/




    companion object {
        val Factory  = viewModelFactory {
            initializer {
                val collRepository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PackAssistApplication).container.collectionsRepository
                val itemRepository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PackAssistApplication).container.itemsRepository
                CollectionCreationViewModel(
                    savedStateHandle = this.createSavedStateHandle(),
                    collectionsRepository = collRepository,
                    itemsRepository = itemRepository
                )
            }
        }
    }
}

data class CollectionUiState(
    val name: String = "",
    val newItem: String = "",
    val items: List<String> = listOf(),
    val isValid: Boolean = false
)


