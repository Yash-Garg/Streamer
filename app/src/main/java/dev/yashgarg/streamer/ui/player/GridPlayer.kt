package dev.yashgarg.streamer.ui.player

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.yashgarg.streamer.data.models.StreamConfig
import dev.yashgarg.streamer.ui.home.HomeViewModel

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("AuthLeak")
@Composable
fun GridPlayer(
    viewModel: HomeViewModel = viewModel()
) {
    val state = viewModel.state
    if (!state.isLoading || state.configs.isNotEmpty()) {
        LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
            items(state.configs) { VideoPlayer(config = it) }
        }
    }
}
