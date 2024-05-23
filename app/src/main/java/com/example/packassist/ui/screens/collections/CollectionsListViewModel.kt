package com.example.packassist.ui.screens.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.packassist.PackAssistApplication
import com.example.packassist.data.entitiesAndDaos.ItemsOfCollection
import com.example.packassist.data.repositories.CollectionsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class CollectionsListUiState(
    val collList: List<ItemsOfCollection> = listOf()
)
class CollectionsListViewModel(
    private val collectionsRepository: CollectionsRepository
) : ViewModel() {
    val collectionsListUiState: StateFlow<CollectionsListUiState> =
        collectionsRepository.getCollectionsWithItemsStream().map { CollectionsListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CollectionsListUiState()
            )









    companion object {
        val Factory  = viewModelFactory {
            initializer {
                val collRepository = (this[APPLICATION_KEY] as PackAssistApplication).container.collectionsRepository
                CollectionsListViewModel(
                    collectionsRepository = collRepository,
                )
            }
        }
    }
}
