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
import androidx.media3.common.util.UnstableApi
import androidx.annotation.OptIn
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_WHEN_PLAYING
import dev.yashgarg.streamer.data.models.StreamConfig

@Composable
@OptIn(UnstableApi::class)
fun VideoPlayer(modifier: Modifier = Modifier, streamUri: StreamConfig) {
    val context = LocalContext.current

    val mediaSource =
        RtspMediaSource.Factory()
            .setForceUseRtpTcp(streamUri.forceRtpTcp)
            .createMediaSource(MediaItem.fromUri(streamUri.toString()))

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaSource(mediaSource)
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
                    setShowBuffering(SHOW_BUFFERING_WHEN_PLAYING)

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
