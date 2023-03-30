package dev.yashgarg.streamer.ui.player

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.rtsp.RtspMediaSource
import androidx.media3.ui.PlayerView

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun VideoPlayer(modifier: Modifier = Modifier, streamUri: List<String>) {
    val context = LocalContext.current

    val mediaSources = streamUri.map { RtspMediaSource.Factory()
        .setForceUseRtpTcp(true)
        .createMediaSource(MediaItem.fromUri(it))
    }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaSources(mediaSources)
            prepare()
            playWhenReady = true
        }
    }

    Box(modifier = modifier) {
        DisposableEffect(Unit) { onDispose { exoPlayer.release() } }

        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    layoutParams =
                        FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams
                                .MATCH_PARENT,
                            ViewGroup.LayoutParams
                                .MATCH_PARENT
                        )
                }
            }
        )
    }
}
