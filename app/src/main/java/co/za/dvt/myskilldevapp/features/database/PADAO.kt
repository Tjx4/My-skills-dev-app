package co.za.dvt.myskilldevapp.features.database

import androidx.lifecycle.LiveData
import androidx.room.*
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable

@Dao
interface PADAO {
    @Insert
    fun insert(usersTable: UsersTable)

    @Update
    fun update(usersTable: UsersTable)

    @Delete
    fun delete(usersTable: UsersTable)

    @Query("SELECT * FROM users WHERE id = :key")
    fun get(key: Long): UsersTable

    @Query("SELECT * FROM users ORDER BY id DESC LIMIT 1")
    fun getFirstUser(): UsersTable

    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAllUsersLiveData(): LiveData<List<UsersTable>>

    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAllUsers():List<UsersTable>

    @Query("DELETE  FROM users")
    fun clear()
}