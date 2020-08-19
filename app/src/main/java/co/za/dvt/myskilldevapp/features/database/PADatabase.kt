package co.za.dvt.myskilldevapp.features.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable

@Database(entities = [UsersTable::class], version = 1, exportSchema = false)
abstract class PADatabase : RoomDatabase() {
    abstract val PADAO: PADAO

    companion object{
        @Volatile
        private var INSTANCE: PADatabase? = null

        fun getInstance(context: Context): PADatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, PADatabase::class.java, "pa_database")
                                    .fallbackToDestructiveMigration()
                                    .build()
                    INSTANCE = instance
                }

                return  instance
            }
        }
    }

}