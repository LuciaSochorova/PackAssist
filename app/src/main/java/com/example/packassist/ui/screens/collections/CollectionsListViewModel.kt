package com.example.packassist.ui.screens.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.packassist.PackAssistApplication
import com.example.packassist.data.entitiesAndDaos.ItemsOfCollection
import com.example.packassist.data.repositories.CollectionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * The UI state for the CollectionListScreen.
 *
 * @property collectionList The list of collections to display.
 * @constructor Create empty Collections list ui state
 */
data class CollectionsListUiState(
    val collectionList: List<ItemsOfCollection> = listOf()
)

/**
 * A ViewModel for the CollectionListScreen.
 *
 * @property collectionsRepository The repository for collections.
 * @constructor Create empty Collections list view model
 */
class CollectionsListViewModel(
    private val collectionsRepository: CollectionsRepository
) : ViewModel() {
    private val _filterQueryFlow = MutableStateFlow("")
    private val _collectionsListUiState =
        collectionsRepository.getAllNoEventCollectionsWithItems().map { CollectionsListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = CollectionsListUiState()
            )

    /**
     * The UiState for the collections list screen.
     */
    val collectionsListUiState: StateFlow<CollectionsListUiState> =
        combine(_collectionsListUiState, _filterQueryFlow) { collection, filterQuery ->
            CollectionsListUiState(collectionList = collection.collectionList.filter {
                it.collection.name.contains(
                    filterQuery,
                    ignoreCase = true
                ) || it.items.any{ item -> item.name.contentEquals(filterQuery, ignoreCase = true)}
            })
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = CollectionsListUiState()
        )


    /**
     * Filters the collections based on the given query.
     *
     * @param query The query to filter the collections by.
     */
    fun filter(query: String) {
        _filterQueryFlow.update {query}
    }

    /**
     * A companion object for the [CollectionsListViewModel] class.
     *
     * Contains the [Factory] property, which is a [ViewModelProvider.Factory] that creates instances of [CollectionsListViewModel].
     */
    companion object {
        val Factory = viewModelFactory {
            initializer {
                val collRepository =
                    (this[APPLICATION_KEY] as PackAssistApplication).container.collectionsRepository
                CollectionsListViewModel(
                    collectionsRepository = collRepository,
                )
            }
        }
    }
}
