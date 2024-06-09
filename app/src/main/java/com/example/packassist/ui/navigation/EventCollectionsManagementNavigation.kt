package com.example.packassist.ui.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.events.EventCollectionsManagementViewModel
import com.example.packassist.ui.screens.events.ManageEventCollectionsScreen

private const val EVENT_COLL_MANAGEMENT_ROUTE = "event_coll_management/{$EVENT_ID_ARG}"

/**
 * Adds the event collections management screen to the navigation graph.
 *
 * @param onBack Callback to navigate back to the previous screen.
 * @param onAddCollection Callback to navigate to the add new collection screen.
 * @param onCollectionClick Callback to navigate to the collection details screen.
 * @receiver The NavGraphBuilder object that is used to construct the navigation graph.
 */
fun NavGraphBuilder.eventCollectionsManagementScreen(
    onBack: () -> Unit,
    onAddCollection: (Int) -> Unit,
    onCollectionClick: (Int) -> Unit
) {
    composable(
        route = EVENT_COLL_MANAGEMENT_ROUTE
    ) {
        val viewModel: EventCollectionsManagementViewModel =
            viewModel(factory = EventCollectionsManagementViewModel.Factory)

        val uiState by viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current)

        ManageEventCollectionsScreen(
            uiState = uiState,
            backAction = onBack,
            onAddCollection = onAddCollection,
            onCollectionClick = onCollectionClick
        )
    }
}

/**
 * Navigates to the event collections management screen.
 *
 * @param eventId The ID of the event.
 * @receiver The NavController object that is used to navigate between screens.
 */
fun NavController.navigateToEventsCollectionManagement(eventId: Int) {
    this.navigate(EVENT_COLL_MANAGEMENT_ROUTE.replace("{$EVENT_ID_ARG}", eventId.toString()))
}


