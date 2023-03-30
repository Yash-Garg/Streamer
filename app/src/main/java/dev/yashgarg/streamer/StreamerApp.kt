package dev.yashgarg.streamer

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import dev.yashgarg.streamer.ui.player.VideoPlayer

@Composable
fun StreamerApp(windowSizeClass: WindowSizeClass) {
    val showTopAppBar =
        windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact

    MainApp()
}

@Composable
fun MainApp() {
    VideoPlayer(streamUri = listOf())
}
