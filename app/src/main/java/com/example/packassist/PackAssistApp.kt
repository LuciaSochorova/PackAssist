package com.example.packassist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.packassist.navigation.CollectionsListRoute
import com.example.packassist.navigation.collectionsListScreen


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
            onAddNewCollection = {/*TODO*/}
        )
    }
}

