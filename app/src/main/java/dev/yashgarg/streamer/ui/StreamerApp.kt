package dev.yashgarg.streamer.ui

import android.net.Uri
import android.os.Build
import android.util.Log
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
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import dev.yashgarg.streamer.R
import dev.yashgarg.streamer.data.AssetParamType
import dev.yashgarg.streamer.data.models.NavDestinations
import dev.yashgarg.streamer.data.models.StreamConfig
import dev.yashgarg.streamer.ui.config.ConfigScreen
import dev.yashgarg.streamer.ui.config.ConfigViewModel
import dev.yashgarg.streamer.ui.home.HomeScreen
import dev.yashgarg.streamer.ui.home.HomeViewModel
import dev.yashgarg.streamer.ui.player.VideoPlayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StreamerApp(windowSizeClass: WindowSizeClass) {
    val context = LocalContext.current
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        context.getString(R.string.app_name),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.TwoTone.Settings, contentDescription = null)
                    }
                },
            )
        },
    ) { paddingValies ->
        NavHost(
            modifier = Modifier.padding(paddingValies), navController = navController,
            startDestination = NavDestinations.HomeScreen.route
        ) {
            composable(NavDestinations.HomeScreen.route) {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                HomeScreen(
                    viewModel = homeViewModel,
                    onAddClick = {
                        navController.navigate(NavDestinations.ConfigScreen.route)
                    },
                    onStreamClick = { config ->
                        val json = Uri.encode(Gson().toJson(config))
                        navController.navigate("${NavDestinations.PlayerScreen.route}/$json")
                    }
                )
            }

            composable(NavDestinations.ConfigScreen.route) {
                val configViewModel = hiltViewModel<ConfigViewModel>()
                ConfigScreen(
                    viewModel = configViewModel,
                    onConfigSaved = { navController.navigateUp() }
                )
            }

            composable(
                "${NavDestinations.PlayerScreen.route}/{config}",
                arguments = listOf(
                    navArgument("config") { type = AssetParamType() }
                )
            ) {
                val streamConfig = it.arguments?.getParcelable<StreamConfig>("config")
                streamConfig?.let { VideoPlayer(streamUri = streamConfig) }
            }
        }
    }
}
