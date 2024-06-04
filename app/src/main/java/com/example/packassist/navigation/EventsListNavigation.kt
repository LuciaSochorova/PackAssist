package com.example.packassist.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.events.EventsListScreen
import com.example.packassist.ui.screens.events.EventsListViewModel

const val EVENT_LIST_ROUTE = "events"

fun NavGraphBuilder.eventsListScreen(
    onAddNewEvent: () -> Unit,
    onEventClick: (Int) -> Unit,
    onNavigateToRoute: (route: String) -> Unit
) {
    composable(route = EVENT_LIST_ROUTE) {
        val viewModel: EventsListViewModel = viewModel(factory = EventsListViewModel.Factory)
        val uiState by viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current)
        EventsListScreen(
            uiState = uiState,
            onAddNewEvent = onAddNewEvent,
            onEventClick = onEventClick,
            route = EVENT_LIST_ROUTE,
            onNavigateToRoute = onNavigateToRoute,
            filterEvents = viewModel::filterEvents
        )
    }

}

fun NavController.navigateToEventsList() {
    this.navigate(EVENT_LIST_ROUTE)
}