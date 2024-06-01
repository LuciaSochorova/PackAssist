package com.example.packassist.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.events.EventCollectionsManagementViewModel
import com.example.packassist.ui.screens.events.ManageEventCollectionsScreen

private const val EVENT_COLL_MANAGEMENT_ROUTE = "event_coll_management/{$EventIdArg}"
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

fun NavController.navigateToEventsCollectionManagement(eventId: Int) {
    this.navigate(EVENT_COLL_MANAGEMENT_ROUTE.replace("{$EventIdArg}", eventId.toString()))
}


