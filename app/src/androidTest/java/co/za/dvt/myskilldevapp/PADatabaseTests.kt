package co.za.dvt.myskilldevapp

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.database.PADAO
import co.za.dvt.myskilldevapp.features.database.PADatabase
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PADatabaseTests{

    private lateinit var PADAO: PADAO
    private lateinit var PADatabase: PADatabase

    @Before
    fun createDB(){
        var context = InstrumentationRegistry.getInstrumentation().targetContext

        PADatabase = Room.inMemoryDatabaseBuilder(context, PADatabase::class.java)
                                .allowMainThreadQueries()
                                .build()

        PADAO = PADatabase.PADAO
    }

    @After
    @Throws(IOException::class)
    fun closeDB(){
        PADatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetGameStats(){
        var gameStats = UsersTable()
        gameStats.id = 404594

        PADAO.insert(gameStats)
        val currentGameStats = PADAO.get(gameStats.id)

        assertEquals(currentGameStats.id, gameStats.id)
    }

}