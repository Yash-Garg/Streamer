package dev.yashgarg.streamer.ui

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import dev.yashgarg.streamer.data.AssetParamType
import dev.yashgarg.streamer.data.models.NavDestinations
import dev.yashgarg.streamer.data.models.StreamConfig
import dev.yashgarg.streamer.ui.config.ConfigScreen
import dev.yashgarg.streamer.ui.config.ConfigViewModel
import dev.yashgarg.streamer.ui.home.HomeScreen
import dev.yashgarg.streamer.ui.home.HomeViewModel
import dev.yashgarg.streamer.ui.player.GridPlayer
import dev.yashgarg.streamer.ui.player.VideoPlayer

@Composable
fun StreamerApp(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()

    Scaffold { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = NavDestinations.HomeScreen.route
        ) {
            composable(NavDestinations.HomeScreen.route) {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                HomeScreen(
                    viewModel = homeViewModel,
                    windowSizeClass = windowSizeClass,
                    onAddClick = { navController.navigate(NavDestinations.ConfigScreen.route) },
                    onStreamClick = { config ->
                        val json = Uri.encode(Gson().toJson(config))
                        navController.navigate("${NavDestinations.PlayerScreen.route}/$json")
                    },
                    onGridClick = { navController.navigate(NavDestinations.GridPlayerScreen.route) }
                )
            }

            composable(NavDestinations.ConfigScreen.route) {
                val configViewModel = hiltViewModel<ConfigViewModel>()
                ConfigScreen(
                    viewModel = configViewModel,
                    onBackNavigate = { navController.navigateUp() }
                )
            }

            composable(
                "${NavDestinations.PlayerScreen.route}/{config}",
                arguments = listOf(navArgument("config") { type = AssetParamType() })
            ) { backstack ->
                val streamConfig = backstack.arguments?.getParcelable<StreamConfig>("config")
                streamConfig?.let { VideoPlayer(config = it) }
            }

            composable(NavDestinations.GridPlayerScreen.route) {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                GridPlayer(viewModel = homeViewModel)
            }
        }
    }
}
