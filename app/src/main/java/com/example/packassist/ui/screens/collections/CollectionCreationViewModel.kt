package com.example.packassist.ui.screens.collections

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.packassist.data.entitiesAndDaos.Collection
import com.example.packassist.data.entitiesAndDaos.Item
import com.example.packassist.data.repositories.CollectionsRepository
import com.example.packassist.data.repositories.ItemsRepository

class CollectionCreationViewModel(
    private val collectionsRepository: CollectionsRepository,
    private val itemsRepository: ItemsRepository
) {
    var state by mutableStateOf(CollectionUiState())
        private set

    fun onNameChange(name: String) {
        state = state.copy(name = name)
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

    suspend fun saveCollection() {
        if (state.validForInsertion) {
            val rowId = collectionsRepository.insertCollection(
                Collection(name = state.name)
            )

            val coll = collectionsRepository.getCollectionId(rowId)

            state.items.forEach { name ->
                itemsRepository.upsertItem(itemToItem(name = name, collection = coll))
            }

        }
    }

    fun validate() {
        state = state.copy(
            validForInsertion =
            (state.items.isNotEmpty() && state.name.isNotEmpty())
        )
    }

    private fun itemToItem(name: String, collection: Int): Item = Item(
        name = name,
        collection = collection
    )

    /*TODO IMPORT*/
}

data class CollectionUiState(
    val name: String = "",
    val newItem: String = "",
    val items: List<String> = listOf(),
    val validForInsertion: Boolean = false
)


