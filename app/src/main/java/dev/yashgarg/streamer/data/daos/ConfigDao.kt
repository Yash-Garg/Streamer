package dev.yashgarg.streamer.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.yashgarg.streamer.data.models.StreamConfig
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfigDao {
    @Query("SELECT * FROM configs") fun getConfigs(): Flow<List<StreamConfig>>

    @Query("SELECT * FROM configs WHERE config_id = :index")
    suspend fun getConfigAtIndex(index: Int = 0): StreamConfig?

    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun addConfig(config: StreamConfig)
}
