package com.example.packassist.navigation

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.events.EventDetailsScreen
import com.example.packassist.ui.screens.events.EventDetailsViewModel

/**
 * The argument key for the event ID.
 */
const val EVENT_ID_ARG = "eventId"
private const val EVENT_DETAILS_ROUTE = "eventDetails/{$EVENT_ID_ARG}"

/**
 * Adds the event details screen to the navigation graph.
 *
 * @param onBack Callback to navigate back to the previous screen.
 * @param onManageCollections Callback to navigate to the event collections management screen.
 * @receiver Adds the event details screen to the navigation graph.
 */
fun NavGraphBuilder.eventDetailsScreen(
    onBack: () -> Unit,
    onManageCollections: (Int) -> Unit
) {
    composable(
        route = EVENT_DETAILS_ROUTE,
    ) {
        val viewModel: EventDetailsViewModel =
            viewModel(factory = EventDetailsViewModel.Factory)

        val uiState =
            viewModel.state.collectAsStateWithLifecycle(
                lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current
            )

        EventDetailsScreen(
            uiState = uiState.value,
            onEventNameChange = viewModel::changeName,
            onItemCheckedChange = viewModel::packItem,
            navigateBack = onBack,
            saveChanges = viewModel::saveEventInformation,
            onWriteNotes = viewModel::writeNotes,
            onNotesChange = viewModel::changeNotes,
            onLocationChange = viewModel::changeLocation,
            onDateChange = viewModel::changeDate,
            onPickDate = viewModel::pickDate,
            floatingButtonAction = onManageCollections,
            onDelete = viewModel::deleteEvent,
            changeExpand = viewModel::changeExpandCollection
        )

    }
}

/**
 * Navigates to the event details screen.
 *
 * @param eventId The ID of the event.
 * @receiver The NavController object that is used to navigate between screens.
 */
fun NavController.navigateToEventDetails(eventId: Int) {
    this.navigate(EVENT_DETAILS_ROUTE.replace("{$EVENT_ID_ARG}", eventId.toString()))
}
