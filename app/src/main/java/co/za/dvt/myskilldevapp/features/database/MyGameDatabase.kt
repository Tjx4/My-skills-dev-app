package co.za.dvt.myskilldevapp.features.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.za.dvt.myskilldevapp.features.database.tables.GameStats

@Database(entities = [GameStats::class], version = 1, exportSchema = false)
abstract class MyGameDatabase : RoomDatabase() {
    abstract val gameStatsDAO: GameStatsDAO

    companion object{
        @Volatile
        private var INSTANCE: MyGameDatabase? = null

        fun getInstance(context: Context): MyGameDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, MyGameDatabase::class.java, "game_stats_database")
                                    .fallbackToDestructiveMigration()
                                    .build()
                    INSTANCE = instance
                }

                return  instance
            }
        }
    }

}