package com.example.packassist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.packassist.navigation.CollectionsListRoute
import com.example.packassist.navigation.EventsListRoute
import com.example.packassist.navigation.collectionCreationScreen
import com.example.packassist.navigation.collectionEditScreen
import com.example.packassist.navigation.collectionsListScreen
import com.example.packassist.navigation.eventsListScreen
import com.example.packassist.navigation.navigateToBottomBarRoute
import com.example.packassist.navigation.navigateToCollectionCreation
import com.example.packassist.navigation.navigateToCollectionEdit


@Composable
fun PackAssistApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = EventsListRoute,
        modifier = Modifier
    )
    {
        collectionsListScreen(
            onEditCollection = { collectionId -> navController.navigateToCollectionEdit(collectionId.toString()) },
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
            onAddNewEvent = {/*TODO*/},
            onNavigateToRoute = navController::navigateToBottomBarRoute,
            onEventClick = {/*TODO*/}
        )
    }
}

