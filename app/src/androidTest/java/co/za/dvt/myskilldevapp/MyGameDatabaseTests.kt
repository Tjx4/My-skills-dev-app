package co.za.dvt.myskilldevapp

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.database.PADAO
import co.za.dvt.myskilldevapp.features.database.MyGameDatabase
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MyGameDatabaseTests{

    private lateinit var PADAO: PADAO
    private lateinit var myGameDatabase: MyGameDatabase

    @Before
    fun createDB(){
        var context = InstrumentationRegistry.getInstrumentation().targetContext

        myGameDatabase = Room.inMemoryDatabaseBuilder(context, MyGameDatabase::class.java)
                                .allowMainThreadQueries()
                                .build()

        PADAO = myGameDatabase.PADAO
    }

    @After
    @Throws(IOException::class)
    fun closeDB(){
        myGameDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetGameStats(){
        var gameStats = UsersTable()
        gameStats.gameId = 404594

        PADAO.insert(gameStats)
        val currentGameStats = PADAO.get(gameStats.gameId)

        assertEquals(currentGameStats.gameId, gameStats.gameId)
    }

}