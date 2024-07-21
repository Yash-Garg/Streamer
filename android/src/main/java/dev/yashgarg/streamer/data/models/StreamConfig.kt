package dev.yashgarg.streamer.data.models

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(tableName = "configs")
data class StreamConfig(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("config_id") val configId: Int = 0,
    val streamName: String,
    val ip: String,
    val port: Int,
    val path: String,
    val username: String? = null,
    val password: String? = null,
    @ColumnInfo(defaultValue = "0") val forceRtpTcp: Boolean
) : Parcelable {
    override fun toString(): String {
        return if (username == null && password == null) {
            "rtsp://$ip:$port/$path"
        } else {
            "rtsp://$username:$password@$ip:$port/$path"
        }
    }
}
