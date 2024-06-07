package com.example.packassist.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.example.packassist.ui.screens.events.EventCreationDialog
import com.example.packassist.ui.screens.events.EventCreationViewModel

private const val EVENT_CREATION_ROUTE = "EventCreation"

/**
 * Adds the event creation dialog to the navigation graph.
 *
 * @param navigateToEditEvent Callback to navigate to the event details screen.
 * @param onDismiss Callback to dismiss the dialog.
 * @receiver The NavGraphBuilder object that is used to construct the navigation graph.
 */
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

/**
 * Navigates to the event creation dialog.
 *
 * @receiver The NavController object that is used to navigate between screens.
 */
fun NavController.navigateToEventCreationDialog() {
    this.navigate(EVENT_CREATION_ROUTE)
}