package dev.yashgarg.streamer.data

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import dev.yashgarg.streamer.data.models.StreamConfig

class AssetParamType : NavType<StreamConfig>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): StreamConfig? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, StreamConfig::class.java)
        } else {
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): StreamConfig {
        return Gson().fromJson(value, StreamConfig::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: StreamConfig) {
        bundle.putParcelable(key, value)
    }
}
