package com.example.packassist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.packassist.navigation.NavigationBarRoutes


/**
 * Top bar that contains search field and button
 *
 * @param onSearch The callback to be invoked when the search query is submitted.
 * @param icon The icon to be displayed on the button.
 * @param buttonOnClick The callback to be invoked when the button is clicked.
 * @param modifier The modifier to be applied to the top bar.
 * @param contentColor The color of the button.
 * @param iconColor The color of the icon.
 * @param searchLabel The label to be displayed in the search field.
 * @param iconContentDescription The content description for the icon.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarAndButtonTopBar(
    onSearch: (String) -> Unit,
    icon: ImageVector,
    buttonOnClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    iconColor: Color = MaterialTheme.colorScheme.onPrimary,
    searchLabel: String? = null,
    iconContentDescription: String? = null,

    ) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .windowInsetsPadding(TopAppBarDefaults.windowInsets)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                .fillMaxWidth()


        ) {
            val focusManager = LocalFocusManager.current
            SearchField(
                onSearch = onSearch,
                labelText = searchLabel,
                keyboardActions = KeyboardActions { focusManager.clearFocus() },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            IconButton(
                onClick = buttonOnClick,
                modifier = Modifier
                    .background(contentColor, shape = MaterialTheme.shapes.extraSmall)
                    .border(
                        color = MaterialTheme.colorScheme.outline,
                        width = 1.dp,
                        shape = MaterialTheme.shapes.extraSmall
                    )


            ) {
                Icon(
                    icon,
                    contentDescription = iconContentDescription,
                    tint = iconColor,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        HorizontalDivider()
    }
}


/**
 * Top bar with three icons: one on the left and two on the right.
 *
 * @param firstIcon The icon to be displayed on the first (left) button.
 * @param secondIcon The icon to be displayed on the second button.
 * @param thirdIcon The icon to be displayed on the third button.
 * @param firstButtonOnClick The callback to be invoked when the first (left) button is clicked.
 * @param secondButtonOnClick The callback to be invoked when the second button is clicked.
 * @param thirdButtonOnClick The callback to be invoked when the third button is clicked.
 * @param modifier The modifier to be applied to the top bar.
 * @param firstIconContentDescription The content description for the first icon.
 * @param secondIconContentDescription The content description for the second icon.
 * @param thirdIconContentDescription The content description for the third icon.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreeIconButtonsTopBar(
    firstIcon: ImageVector,
    secondIcon: ImageVector,
    thirdIcon: ImageVector,
    firstButtonOnClick: () -> Unit,
    secondButtonOnClick: () -> Unit,
    thirdButtonOnClick: () -> Unit,
    modifier: Modifier = Modifier,
    firstIconContentDescription: String? = null,
    secondIconContentDescription: String? = null,
    thirdIconContentDescription: String? = null

) {
    Column(
        modifier = modifier
            .windowInsetsPadding(TopAppBarDefaults.windowInsets)
            .background(MaterialTheme.colorScheme.surfaceContainerLow)

    ) {
        val iconColors = IconButtonDefaults.iconButtonColors().copy(
            contentColor = MaterialTheme.colorScheme.onSurface
        )
        val iconModifier = Modifier.fillMaxSize()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                .fillMaxWidth()

        ) {
            Row(modifier = Modifier.weight(1f)) {
                IconButton(
                    onClick = firstButtonOnClick,
                    colors = iconColors
                ) {
                    Icon(
                        firstIcon,
                        contentDescription = firstIconContentDescription,
                        modifier = iconModifier
                    )

                }
            }
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.weight(1f)) {
                IconButton(
                    onClick = secondButtonOnClick,
                    colors = iconColors
                ) {
                    Icon(
                        secondIcon,
                        contentDescription = secondIconContentDescription,
                        modifier = iconModifier
                    )

                }

                IconButton(
                    onClick = thirdButtonOnClick,
                    colors = iconColors,
                    modifier = Modifier
                        .padding(start = 36.dp)
                ) {
                    Icon(
                        thirdIcon,
                        contentDescription = thirdIconContentDescription,
                        modifier = iconModifier
                    )

                }
            }
        }

        HorizontalDivider()
    }
}

/**
 * Bottom navigation bar with multiple tabs.
 * This composable displays a bottom navigation bar with multiple tabs, each representing a different navigation destination.
 *
 * @param onNavigateToRoute The callback to be invoked when a tab is clicked.
 * @param currentRoute The current route, used to highlight the selected tab.
 */
@Composable
fun BottomNavBar(
    onNavigateToRoute: (String) -> Unit,
    currentRoute: String,
) {
    BottomAppBar(
        actions = {
            val tabs = listOf(NavigationBarRoutes.COLLECTIONS, NavigationBarRoutes.EVENTS)
            NavigationBar {
                tabs.forEach {
                    NavigationBarItem(
                        selected = it.route == currentRoute,
                        onClick = { onNavigateToRoute(it.route) },
                        label = { Text(text = stringResource(id = it.label)) },
                        icon = {
                            Icon(
                                ImageVector.vectorResource(it.iconRes),
                                contentDescription = null
                            )
                        },
                    )
                }
            }
        }
    )
}

