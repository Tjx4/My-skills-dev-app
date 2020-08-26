package co.za.dvt.myskilldevapp.features.login

import android.app.Application
import co.za.dvt.myskilldevapp.features.database.PADatabase
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import co.za.dvt.myskilldevapp.models.LoginModel

open class LoginRepository(var application: Application) : BaseRepositories() {
    var database = PADatabase.getInstance(application).PADAO

    fun getAllCachedUsers() = database.getAllUsers()
    fun getLastCachedUser() = database.getFirstUser()

    suspend fun loginMember(params: Map<String, String>) : LoginModel?{
        try {
            return retrofitHelper.loginMember(params)
        }
        catch (ex: Exception){
            return LoginModel(false, "$ex", null)
        }
    }

    //fun getUserFromDb(id: Int) = database.get(id.toLong())
    suspend fun addUserToDb(usersTable: UsersTable) = database.insert(usersTable)
}