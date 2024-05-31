package com.example.packassist.navigation

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.events.EventDetailsScreen
import com.example.packassist.ui.screens.events.EventDetailsViewModel


const val EventIdArg = "eventId"
const val EventDetailsRoute = "eventDetails/{$EventIdArg}"

fun NavGraphBuilder.eventDetailsScreen(
    onBack: () -> Unit
) {
    composable(
        route = EventDetailsRoute,
    ) {
        val viewModel: EventDetailsViewModel =
            viewModel(factory = EventDetailsViewModel.Factory)

        val uiState = viewModel.state.collectAsState()

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
            floatingButtonAction = {/*TODO*/},
            deleteAction = viewModel::deleteEvent
        )

    }
}

fun NavController.navigateToEventDetails(eventId : Int){
    this.navigate(EventDetailsRoute.replace("{$EventIdArg}", eventId.toString()))
}
