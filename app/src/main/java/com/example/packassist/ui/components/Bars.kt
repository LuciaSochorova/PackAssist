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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.packassist.navigation.NavigationBarRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextAndIconButtonTopBar(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    iconContentDescription: String? = null,
    buttonOnClick: () -> Unit
) {
    Column(modifier = modifier.windowInsetsPadding(TopAppBarDefaults.windowInsets)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                .fillMaxWidth()


        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.displayMedium,
            )
            IconButton(
                onClick = buttonOnClick,
                modifier = Modifier
                    .wrapContentSize()
                    .border(
                        color = MaterialTheme.colorScheme.scrim,
                        width = 1.dp
                    )


            ) {
                Icon(
                    icon,
                    contentDescription = iconContentDescription,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        HorizontalDivider()
    }
}


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
            .background(MaterialTheme.colorScheme.primaryContainer)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                .fillMaxWidth()

        ) {
            Row(modifier = Modifier.weight(1f)) {
                IconButton(
                    onClick = firstButtonOnClick,
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Icon(
                        firstIcon,
                        contentDescription = firstIconContentDescription,
                        modifier = Modifier.fillMaxSize()
                    )

                }
            }
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.weight(1f)) {
                IconButton(
                    onClick = secondButtonOnClick,
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Icon(
                        secondIcon,
                        contentDescription = secondIconContentDescription,
                        modifier = Modifier.fillMaxSize()
                    )

                }

                IconButton(
                    onClick = thirdButtonOnClick,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 36.dp)
                ) {
                    Icon(
                        thirdIcon,
                        contentDescription = thirdIconContentDescription,
                        modifier = Modifier.fillMaxSize()
                    )

                }
            }
        }


        HorizontalDivider()
    }
}

@Composable
fun BottomNavBar(
    onNavigateToRoute: (String) -> Unit,
    currentRoute: String,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        actions = {
            val tabs = listOf(NavigationBarRoutes.COLLECTIONS, NavigationBarRoutes.EVENTS)
            NavigationBar {
                tabs.forEach {
                    NavigationBarItem(
                        selected = it.route == currentRoute,
                        onClick = { onNavigateToRoute(it.route) },
                        label = {Text(text = stringResource(id = it.label))},
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

