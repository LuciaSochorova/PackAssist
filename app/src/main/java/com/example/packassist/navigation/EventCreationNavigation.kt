package com.example.packassist.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.example.packassist.ui.screens.events.EventCreationDialog
import com.example.packassist.ui.screens.events.EventCreationViewModel

private const val EVENT_CREATION_ROUTE = "EventCreation"

fun NavGraphBuilder.eventCreationDialog(
    navigateToEditEvent: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    dialog(route = EVENT_CREATION_ROUTE) {
        val viewModel: EventCreationViewModel = viewModel(factory = EventCreationViewModel.Factory)
        EventCreationDialog(
            state = viewModel.state.value,
            onDismiss = onDismiss,
            saveEvent = viewModel::saveEvent,
            navigateToEventDetails = navigateToEditEvent,
            onNameChange = viewModel::changeName
        )
    }

}

fun NavController.navigateToEventCreationDialog() {
    this.navigate(EVENT_CREATION_ROUTE)
}