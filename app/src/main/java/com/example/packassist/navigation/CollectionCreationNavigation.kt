package com.example.packassist.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.collections.CollectionCreationScreen
import com.example.packassist.ui.screens.collections.CollectionCreationViewModel

const val CollectionCreationRoute = "collectionCreation"

fun NavGraphBuilder.collectionCreationScreen(
    onNavigateBack: () -> Unit
) {
    composable( route = CollectionCreationRoute ) {
        val viewModel: CollectionCreationViewModel = viewModel(factory = CollectionCreationViewModel.Factory)
        val uiState = viewModel.state

        CollectionCreationScreen(
            collectionUiState = uiState,
            onNameChange = viewModel::onNameChange,
            onNewItemChange = viewModel::onNewItemChange,
            onChangeItem = viewModel::onChangeExistingItem,
            saveCollection = viewModel::saveCollection ,
            inputItemAction = viewModel::ifEmptyDeleteItem,
            addItemAction = viewModel::addItem,
            navigateBack = onNavigateBack,
            onImport = { /*todo*/ }
        )

    }
}



fun NavController.navigateToCollectionCreation() {
    this.navigate(CollectionCreationRoute)
}







