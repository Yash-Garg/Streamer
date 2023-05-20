// Code used from https://github.com/dsa28s/compose-video
package dev.yashgarg.streamer.pip

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.os.Build
import android.util.Rational
import androidx.media3.ui.PlayerView

@Suppress("DEPRECATION")
internal fun enterPip(context: Context, defaultPlayerView: PlayerView) {
    if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
        defaultPlayerView.useController = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val params = PictureInPictureParams.Builder()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                params
                    .setTitle("Video Player")
                    .setAspectRatio(Rational(16, 9))
                    .setSeamlessResizeEnabled(true)
            }

            context.findActivity().enterPictureInPictureMode(params.build())
        } else {
            context.findActivity().enterPictureInPictureMode()
        }
    }
}

internal fun Context.isActivityInPipMode() = findActivity().isInPictureInPictureMode

internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Activity not found. Unknown error.")
}
