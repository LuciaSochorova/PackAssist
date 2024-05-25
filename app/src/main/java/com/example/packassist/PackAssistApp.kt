package com.example.packassist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.packassist.navigation.CollectionsListRoute
import com.example.packassist.navigation.collectionCreationScreen
import com.example.packassist.navigation.collectionsListScreen
import com.example.packassist.navigation.navigateToCollectionCreation
import com.example.packassist.navigation.navigateToCollectionsList


@Composable
fun PackAssistApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CollectionsListRoute,
        modifier = Modifier
    )
    {
        collectionsListScreen(
            onEditCollection = { /*todo*/ },
            onAddNewCollection = {navController.navigateToCollectionCreation()}
        )

        collectionCreationScreen(
            onNavigateBack = {
                navController.navigateUp()
            }
        )
    }
}

