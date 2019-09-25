package co.za.dvt.myskilldevapp.features.dashboard

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameStatsDAO {

    @Insert
    fun insert(gameStats: GameStats)

    @Query("SELECT * FROM game_stats_table " + "ORDER BY game_id DESC")
    fun getAllGameStats(): LiveData<List<GameStats>>
}