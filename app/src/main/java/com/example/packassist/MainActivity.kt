package com.example.packassist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.packassist.ui.screens.collections.CollectionEditScreen
import com.example.packassist.ui.screens.collections.CollectionField
import com.example.packassist.ui.screens.collections.CollectionListScreen
import com.example.packassist.ui.screens.collections.CreateCollectionScreen
import com.example.packassist.ui.screens.events.ManageEventCollectionsScreen
import com.example.packassist.ui.theme.PackAssistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PackAssistTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    ManageEventCollectionsScreen(backAction = { /*TODO*/ }, eventName = "Pokusnýýýý  ý ý ý ý ýýý ý ýýý event") {

                    }
                }
            }
        }
    }
}

