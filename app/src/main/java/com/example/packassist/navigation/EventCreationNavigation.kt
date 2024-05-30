package com.example.packassist.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.example.packassist.ui.screens.events.EventCreationDialog
import com.example.packassist.ui.screens.events.EventCreationViewModel

const val EventCreationRoute = "EventCreation"

fun NavGraphBuilder.eventCreationDialog(
    navigateToEditEvent: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    dialog(route = EventCreationRoute) {
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
    this.navigate(EventCreationRoute)
}