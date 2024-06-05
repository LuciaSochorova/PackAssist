package com.example.packassist.navigation

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.events.EventDetailsScreen
import com.example.packassist.ui.screens.events.EventDetailsViewModel


const val EVENT_ID_ARG = "eventId"
private const val EVENT_DETAILS_ROUTE = "eventDetails/{$EVENT_ID_ARG}"

fun NavGraphBuilder.eventDetailsScreen(
    onBack: () -> Unit,
    onManageCollections: (Int) -> Unit
) {
    composable(
        route = EVENT_DETAILS_ROUTE,
    ) {
        val viewModel: EventDetailsViewModel =
            viewModel(factory = EventDetailsViewModel.Factory)

        val uiState = viewModel.state.collectAsStateWithLifecycle(lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current)

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
            deleteAction = viewModel::deleteEvent,
            changeExpand = viewModel::changeExpandCollection
        )

    }
}

fun NavController.navigateToEventDetails(eventId : Int){
    this.navigate(EVENT_DETAILS_ROUTE.replace("{$EVENT_ID_ARG}", eventId.toString()))
}
