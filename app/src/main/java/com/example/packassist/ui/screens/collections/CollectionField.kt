package com.example.packassist.ui.screens.collections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.packassist.R

@Composable
fun CollectionField(
    name: String,
    editAction: () -> Unit,
    modifier: Modifier = Modifier
    // TODO viewModel
) {
    val itemList = listOf(
        "abc",
        "def dafhu",
        "po fuah ua zu",
        "eihufaoui aeu fhoa faef",
        "iaeuhjf  iuahe fuio"
    )
    Column(
        modifier = modifier
            .wrapContentSize()
            .border(
                color = MaterialTheme.colorScheme.scrim,
                width = 1.dp
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineLarge
            )
            IconButton(
                onClick = editAction,
                modifier = Modifier
                    .wrapContentSize()
            ) {
                Icon(
                    Icons.Outlined.Edit,
                    contentDescription = stringResource(R.string.edit_button_description),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)) {
            val textModifier = Modifier.padding(start = 8.dp)
            for (item in itemList) {
                HorizontalDivider()
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = textModifier
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CollectionFieldPreview() {
    CollectionField("Pokusná kolekcia", {/*TODO*/ })
}