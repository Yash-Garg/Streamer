package dev.yashgarg.streamer.ui

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
                    onBackNavigate = { navController.navigateUp() }
                )
            }

            composable(
                "${NavDestinations.PlayerScreen.route}/{config}",
                arguments = listOf(
                    navArgument("config") { type = AssetParamType() }
                )
            ) { backstack ->
                val streamConfig = backstack.arguments?.getParcelable<StreamConfig>("config")
                streamConfig?.let { VideoPlayer(config = it) }
            }
        }
    }
}
