package dev.yashgarg.streamer.ui.player

import android.content.res.Configuration
import android.util.Log
import android.view.SurfaceView
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.rtsp.RtspMediaSource
import androidx.media3.exoplayer.util.EventLogger
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_WHEN_PLAYING
import dev.yashgarg.streamer.data.models.StreamConfig

@Composable
@OptIn(UnstableApi::class)
fun VideoPlayer(config: StreamConfig) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val ratio =
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 16 / 9f else 1f

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

    val listener =
        object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                Toast.makeText(
                        context,
                        "Stream ${config.configId}: ${error.message}",
                        Toast.LENGTH_SHORT
                    )
                    .show()
                super.onPlayerError(error)
            }
        }

    val exoPlayer = remember {
        ExoPlayer.Builder(context, renderer).build().apply {
            setMediaSource(mediaSource)
            setVideoSurfaceView(surface)
            setVideoSurfaceHolder(surface.holder)
            addAnalyticsListener(EventLogger())
            addListener(listener)
            prepare()
            playWhenReady = true
        }
    }

    val defaultPlayerView = remember {
        PlayerView(context).apply {
            player = exoPlayer
            useController = false
            keepScreenOn = true
            layoutParams = FrameLayout.LayoutParams(screenWidth, screenHeight)

            setShowPreviousButton(false)
            setShowNextButton(false)
            setShowSubtitleButton(false)
            setShowFastForwardButton(false)
            setShowRewindButton(false)
            setShowBuffering(SHOW_BUFFERING_WHEN_PLAYING)
        }
    }

    DisposableEffect(Unit) { onDispose { exoPlayer.release() } }

    AndroidView(
        modifier =
            Modifier.fillMaxSize(1f)
                .aspectRatio(
                    ratio,
                    matchHeightConstraintsFirst =
                        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                )
                .onKeyEvent {
                    Log.d("VideoPlayer", "onKeyEvent: $it")
                    true
                },
        factory = { defaultPlayerView }
    )
}
