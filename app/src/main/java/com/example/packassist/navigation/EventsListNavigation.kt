package com.example.packassist.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.events.EventsListScreen
import com.example.packassist.ui.screens.events.EventsListViewModel

const val EventsListRoute = "events"

fun NavGraphBuilder.eventsListScreen(
    onAddNewEvent: () -> Unit,
    onEventClick: (Int) -> Unit,
    onNavigateToRoute: (route: String) -> Unit
) {
    composable(route = EventsListRoute) {
        val viewModel: EventsListViewModel = viewModel(factory = EventsListViewModel.Factory)
        val uiState by viewModel.uiState.collectAsState()
        EventsListScreen(
            uiState = uiState,
            onAddNewEvent = onAddNewEvent,
            onEventClick = onEventClick,
            route = EventsListRoute,
            onNavigateToRoute = onNavigateToRoute
        )
    }

}

fun NavController.navigateToEventsList() {
    this.navigate(EventsListRoute)
}