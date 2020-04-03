package co.za.dvt.myskilldevapp.features.database

import androidx.lifecycle.LiveData
import androidx.room.*
import co.za.dvt.myskilldevapp.features.database.tables.GameStats

@Dao
interface GameStatsDAO {
    @Insert
    fun insert(gameStats: GameStats)

    @Update
    fun update(gameStats: GameStats)

    @Delete
    fun delete(gameStats: GameStats)

    @Query("SELECT * FROM game_stats_table WHERE game_id = :key")
    fun get(key: Long): GameStats

    @Query("SELECT * FROM game_stats_table ORDER BY game_id DESC LIMIT 1")
    fun getCurrentStats(): GameStats

    @Query("SELECT * FROM game_stats_table ORDER BY game_id DESC")
    fun getAllGameStatsLiveData(): LiveData<List<GameStats>>

    @Query("SELECT * FROM game_stats_table ORDER BY game_id DESC")
    fun getAllGameStats():List<GameStats>

    @Query("DELETE  FROM game_stats_table")
    fun clear()
}