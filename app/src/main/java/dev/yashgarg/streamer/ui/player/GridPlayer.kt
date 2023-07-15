package dev.yashgarg.streamer.ui.player

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.yashgarg.streamer.ui.home.HomeViewModel

@Composable
fun GridPlayer(viewModel: HomeViewModel = viewModel()) {
    val conf = LocalConfiguration.current
    val state = viewModel.state

    val cols = if (conf.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2

    if (!state.isLoading || state.configs.isNotEmpty()) {
        val configs =
            if (state.configs.size > 4) {
                state.configs.subList(0, 4)
            } else state.configs

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(cols)) {
                items(configs) { VideoPlayer(config = it) }
            }
        }
    }
}
