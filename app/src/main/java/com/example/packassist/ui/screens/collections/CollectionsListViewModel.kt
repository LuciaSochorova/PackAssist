package com.example.packassist.ui.screens.collections

import androidx.lifecycle.ViewModel
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

data class CollectionsListUiState(
    val collList: List<ItemsOfCollection> = listOf()
)

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

    val collectionsListUiState: StateFlow<CollectionsListUiState> =
        combine(_collectionsListUiState, _filterQueryFlow) { collection, filterQuery ->
            CollectionsListUiState(collList = collection.collList.filter {
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


    fun filter(query: String) {
        _filterQueryFlow.update {query}
    }

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
