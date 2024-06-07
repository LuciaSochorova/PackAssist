package com.example.packassist.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.events.EventsListScreen
import com.example.packassist.ui.screens.events.EventsListViewModel

/**
 * The route for the events list screen.
 */
const val EVENT_LIST_ROUTE = "events"

/**
 * Adds the events list screen to the navigation graph.
 *
 * @param onAddNewEvent Callback to navigate to the event creation screen.
 * @param onEventClick Callback to navigate to the event details screen.
 * @param onNavigateToRoute Callback to navigate to a different screen.
 * @receiver The NavGraphBuilder object that is used to construct the navigation graph.
 */
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

/**
 * Navigates to the events list screen.
 *
 * @receiver The NavController object that is used to navigate between screens.
 */
fun NavController.navigateToEventsList() {
    this.navigate(EVENT_LIST_ROUTE)
}