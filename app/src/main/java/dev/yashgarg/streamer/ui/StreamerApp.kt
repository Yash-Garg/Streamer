package dev.yashgarg.streamer.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.yashgarg.streamer.R
import dev.yashgarg.streamer.data.models.NavDestinations
import dev.yashgarg.streamer.ui.config.ConfigScreen
import dev.yashgarg.streamer.ui.home.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StreamerApp(windowSizeClass: WindowSizeClass) {
    val context = LocalContext.current
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    context.getString(R.string.app_name),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
            },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.TwoTone.Settings, contentDescription = null)
                    }
                }
            )
        },
    ) {
        NavHost(
            modifier = Modifier.padding(it), navController = navController,
            startDestination = NavDestinations.HomeScreen.route
        ) {
            composable(NavDestinations.HomeScreen.route) {
                HomeScreen(onAddClick = {
                    navController.navigate(NavDestinations.ConfigScreen.route)
                })
            }

            composable(NavDestinations.ConfigScreen.route) {
                ConfigScreen()
            }

            composable(NavDestinations.PlayerScreen.route) {
//                VideoPlayer(streamUri = listOf(config))
            }
        }
    }
}
