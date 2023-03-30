package dev.yashgarg.streamer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.yashgarg.streamer.data.daos.ConfigDao
import dev.yashgarg.streamer.data.models.StreamConfig

@Database(
    entities = [StreamConfig::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun configDao(): ConfigDao

    companion object {
        const val DB_NAME = "streamer_db"
    }
}
