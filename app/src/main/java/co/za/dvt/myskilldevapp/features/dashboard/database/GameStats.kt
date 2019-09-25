package co.za.dvt.myskilldevapp.features.dashboard.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_stats_table")
data class GameStats (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    var gameId:Long = 0L,
    @ColumnInfo(name = "player")
    var player:Long = 0L,
    @ColumnInfo(name = "tries")
    var tries:Int = 0,
    @ColumnInfo(name = "jackpot_price")
    var jackpotPrice:String? = null,
    @ColumnInfo(name = "start_time")
    var startTime:Long = System.currentTimeMillis(),
    @ColumnInfo(name = "end_time")
    var endTime:Long = startTime
)