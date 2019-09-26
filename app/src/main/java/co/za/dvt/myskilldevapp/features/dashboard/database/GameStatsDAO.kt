package co.za.dvt.myskilldevapp.features.dashboard.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GameStatsDAO {
    @Insert
    fun insert(gameStats: GameStats)

    @Update
    fun update(gameStats: GameStats)

    @Delete
    fun delete(gameStats: GameStats)

    @Query("SELECT * FROM game_stats_table WHERE game_id = :key")
    fun get(key: Long):GameStats

    @Query("SELECT * FROM game_stats_table ORDER BY game_id DESC")
    fun getAllGameStats(): LiveData<List<GameStats>>


    @Query("DELETE  FROM game_stats_table")
    fun clear()
}