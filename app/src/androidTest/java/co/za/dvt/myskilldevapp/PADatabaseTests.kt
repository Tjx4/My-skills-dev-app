package co.za.dvt.myskilldevapp

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.database.USERSDAO
import co.za.dvt.myskilldevapp.features.database.PADatabase
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PADatabaseTests{
    private lateinit var PADatabase: PADatabase
    private lateinit var USERSDAO: USERSDAO

    @Before
    fun createDB(){
        var context = InstrumentationRegistry.getInstrumentation().targetContext

        PADatabase = Room.inMemoryDatabaseBuilder(context, PADatabase::class.java)
                                .allowMainThreadQueries()
                                .build()

        USERSDAO = PADatabase.USERSDAO
    }

    @After
    @Throws(IOException::class)
    fun closeDB(){
        PADatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetGameStats(){
        var usersTable = UsersTable()
        usersTable.id = 404594

        USERSDAO.insert(usersTable)
        val currentUser = USERSDAO.get(usersTable.id)

        assertEquals(currentUser?.id, usersTable.id)
    }

}