package com.example.packassist.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavController
import com.example.packassist.R


enum class NavigationBarRoutes(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val label: Int,
    ) {
    COLLECTIONS(
        CollectionsListRoute,
        R.drawable.collections_icon,
        R.string.collections_title

    ),
    EVENTS(
        EVENT_LIST_ROUTE,
        R.drawable.event_icon,
        R.string.events_title
    )

}



fun NavController.navigateToBottomBarRoute(route: String) {
    if (route != this.currentDestination?.route) {
        this.navigate(route) {
            popUpTo(EVENT_LIST_ROUTE) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

