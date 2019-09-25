package co.za.dvt.myskilldevapp

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import co.za.dvt.myskilldevapp.features.dashboard.database.GameStats
import co.za.dvt.myskilldevapp.features.dashboard.database.GameStatsDAO
import co.za.dvt.myskilldevapp.features.database.MyGameDatabase
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MyGameDatabaseTests{

    private lateinit var gameStatsDAO: GameStatsDAO
    private lateinit var myGameDatabase: MyGameDatabase

    @Before
    fun createDB(){
        var context = InstrumentationRegistry.getInstrumentation().targetContext
        myGameDatabase = Room.inMemoryDatabaseBuilder(context, MyGameDatabase::class.java).allowMainThreadQueries().build()

        gameStatsDAO = myGameDatabase.gameStatsDAO
    }

    @After
    @Throws(IOException::class)
    fun closeDB(){
        myGameDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetGameStats(){
        var gameStats = GameStats()
        gameStats.gameId = 404594

        gameStatsDAO.insert(gameStats)
        val currentGameStats = gameStatsDAO.get(gameStats.gameId)

        assertEquals(currentGameStats.gameId, gameStats.gameId)
    }
}