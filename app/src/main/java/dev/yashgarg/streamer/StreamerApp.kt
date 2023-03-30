package dev.yashgarg.streamer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun StreamerApp(windowSizeClass: WindowSizeClass) {
    val showTopAppBar =
        windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact

    MainApp()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    "Streamer",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
            },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.TwoTone.Settings, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {}, icon = {
                Icon(Icons.TwoTone.Add, contentDescription = null)
            }, text = {
                Text("ADD STREAM")
            })
        },
    ) {
        Column(modifier = Modifier.padding(it)) {}
    }
}
