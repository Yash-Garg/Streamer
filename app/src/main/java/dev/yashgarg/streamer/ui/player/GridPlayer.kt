package dev.yashgarg.streamer.ui.player

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.yashgarg.streamer.ui.home.HomeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridPlayer(viewModel: HomeViewModel = viewModel()) {
    val state = viewModel.state

    if (!state.isLoading || state.configs.isNotEmpty()) {
        val configs =
            if (state.configs.size > 4) {
                state.configs.subList(0, 4)
            } else state.configs

        LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
            items(configs) { VideoPlayer(config = it) }
        }
    }
}
