package co.za.dvt.myskilldevapp.features.login

import android.app.Application
import co.za.dvt.myskilldevapp.features.database.PADatabase
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories

open class LoginRepository(var application: Application) : BaseRepositories() {
    var database = PADatabase.getInstance(application).PADAO

    fun getLastUser() = database.getFirstUser()
    fun getUserFromDb(id: Int) = database.get(id.toLong())
    fun addUserToDb(usersTable: UsersTable) = database.insert(usersTable)
}