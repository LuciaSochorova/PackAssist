package com.example.packassist.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavController
import com.example.packassist.R


/**
 * Represents the different routes for the bottom navigation bar.
 *
 * @property route Route used to identify the destination.
 * @property iconRes The resource ID of the icon to be displayed.
 * @property label The resource ID of the string to be displayed as the label.
 * @constructor Create empty Navigation bar routes
 */
enum class NavigationBarRoutes(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val label: Int,
    ) {
    /**
     * Collections bottom bar destination
     *
     */
    COLLECTIONS(
        COLLECTIONS_LIST_ROUTE,
        R.drawable.collections_icon,
        R.string.collections_title

    ),

    /**
     * Events bottom bar destination
     *
     */
    EVENTS(
        EVENT_LIST_ROUTE,
        R.drawable.event_icon,
        R.string.events_title
    )

}


/**
 * Navigates to the specified route on the bottom navigation bar.
 *
 * @param route The route to navigate to.
 */
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

