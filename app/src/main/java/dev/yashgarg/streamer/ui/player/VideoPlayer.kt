package dev.yashgarg.streamer.ui.player

import android.view.SurfaceView
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.rtsp.RtspMediaSource
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_WHEN_PLAYING
import dev.yashgarg.streamer.data.models.StreamConfig

@Composable
@OptIn(UnstableApi::class)
fun VideoPlayer(modifier: Modifier = Modifier, config: StreamConfig) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val surface = SurfaceView(context)

    val mediaSource =
        RtspMediaSource.Factory()
            .setForceUseRtpTcp(config.forceRtpTcp)
            .createMediaSource(MediaItem.fromUri(config.toString()))

    val renderer =
        DefaultRenderersFactory(context).apply {
            setEnableDecoderFallback(true)
            setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER)
        }

    val exoPlayer = remember {
        ExoPlayer.Builder(context, renderer).build().apply {
            setMediaSource(mediaSource)
            setVideoSurfaceView(surface)
            setVideoSurfaceHolder(surface.holder)
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) { onDispose { exoPlayer.release() } }

    AndroidView(
        modifier = Modifier.fillMaxSize().then(modifier),
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                layoutParams = FrameLayout.LayoutParams(screenWidth, screenHeight)

                setShowPreviousButton(false)
                setShowNextButton(false)
                setShowSubtitleButton(false)
                setShowFastForwardButton(false)
                setShowRewindButton(false)
                setShowBuffering(SHOW_BUFFERING_WHEN_PLAYING)
            }
        }
    )
}
