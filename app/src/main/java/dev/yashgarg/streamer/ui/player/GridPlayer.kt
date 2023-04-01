package dev.yashgarg.streamer.ui.player

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import dev.yashgarg.streamer.data.models.StreamConfig

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("AuthLeak")
@Composable
fun GridPlayer(configs: List<StreamConfig>) {
    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
        items(configs) {
            VideoPlayer(config = it)
        }
    }
}
