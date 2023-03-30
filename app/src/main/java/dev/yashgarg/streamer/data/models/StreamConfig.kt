package dev.yashgarg.streamer.data.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "configs")
data class StreamConfig(
    @PrimaryKey @ColumnInfo("config_id") val configId: Int,
    val streamName: String,
    val ip: String,
    val port: Int,
    val path: String,
    val username: String? = null,
    val password: String? = null,
    @ColumnInfo(defaultValue = "0") val forceRtpTcp: Boolean
)
