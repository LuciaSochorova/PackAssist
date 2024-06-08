package com.example.packassist


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.packassist.navigation.EVENT_LIST_ROUTE
import com.example.packassist.navigation.collectionCreationScreen
import com.example.packassist.navigation.collectionEditScreen
import com.example.packassist.navigation.collectionsListScreen
import com.example.packassist.navigation.eventCollectionsManagementScreen
import com.example.packassist.navigation.eventCreationDialog
import com.example.packassist.navigation.eventDetailsScreen
import com.example.packassist.navigation.eventsListScreen
import com.example.packassist.navigation.navigateToBottomBarRoute
import com.example.packassist.navigation.navigateToCollectionCreation
import com.example.packassist.navigation.navigateToCollectionEdit
import com.example.packassist.navigation.navigateToEventCreationDialog
import com.example.packassist.navigation.navigateToEventDetails
import com.example.packassist.navigation.navigateToEventsCollectionManagement


/**
 * The root composable of the PackAssist app.
 *
 * This composable sets up the navigation for the app.
 */
@Composable
fun PackAssistApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = EVENT_LIST_ROUTE,
        modifier = Modifier
    )
    {

        collectionsListScreen(
            onEditCollection = { collectionId -> navController.navigateToCollectionEdit(collectionId) },
            onAddNewCollection = { navController.navigateToCollectionCreation() },
            onNavigateToRoute = navController::navigateToBottomBarRoute
        )

        collectionCreationScreen(
            onNavigateBack = {
                navController.navigateUp()
            }
        )

        collectionEditScreen {
            navController.navigateUp()
        }

        eventsListScreen(
            onAddNewEvent = { navController.navigateToEventCreationDialog() },
            onNavigateToRoute = navController::navigateToBottomBarRoute,
            onEventClick = { navController.navigateToEventDetails(it) }
        )

        eventCreationDialog(
            onDismiss = { navController.popBackStack() },
            navigateToEditEvent = navController::navigateToEventDetails
        )

        eventDetailsScreen(
            onBack = {
                navController.popBackStack(EVENT_LIST_ROUTE, false)
            },
            onManageCollections = { navController.navigateToEventsCollectionManagement(it) }
        )

        eventCollectionsManagementScreen(
            onCollectionClick = navController::navigateToCollectionEdit,
            onBack = {navController.navigateUp()},
            onAddCollection = {navController.navigateToCollectionCreation(it)}
        )
    }
}

