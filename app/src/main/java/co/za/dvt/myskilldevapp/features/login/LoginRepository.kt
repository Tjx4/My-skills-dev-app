package co.za.dvt.myskilldevapp.features.login

import android.app.Application
import co.za.dvt.myskilldevapp.features.database.PADatabase
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import co.za.dvt.myskilldevapp.models.LoginModel

open class LoginRepository(var application: Application) : BaseRepositories() {
    var database = PADatabase.getInstance(application).USERSDAO

    suspend fun getAllCachedUsers() = database.getAllUsers()
    suspend fun getLastCachedUser() = database.getLastUser()
    suspend fun addUserToDb(usersTable: UsersTable) = database.insert(usersTable)

    suspend fun loginMember(params: Map<String, String>) : LoginModel{
        // Todo fix
        try {
            return retrofitHelper.loginMember(params) ?: LoginModel(false, "", "", null)
        }
        catch (ex: Exception){
            return LoginModel(false, "$ex", "", null)
        }
    }

}