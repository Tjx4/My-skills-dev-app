package co.za.dvt.myskilldevapp.features.dashboard

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_stats")
data class GameStats (
    @PrimaryKey(autoGenerate = true)
    var gameId:Long = 0L,
    @ColumnInfo(name = "tries")
    var tries:Int = 0,
    @ColumnInfo(name = "jackpot_price")
    var jackpotPrice:String,
    @ColumnInfo(name = "player")
    var player:Long = 0L
)