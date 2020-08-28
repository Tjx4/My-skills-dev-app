package co.za.dvt.myskilldevapp.features.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.features.database.PADatabase
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import co.za.dvt.myskilldevapp.models.LoginModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class LoginRepository(var application: Application) : BaseRepositories() {
    var database = PADatabase.getInstance(application).USERSDAO

    suspend fun getAllCachedUsers() = database.getAllUsers()
    suspend fun getLastCachedUser() = database.getLastUser()
    suspend fun addUserToDb(usersTable: UsersTable) = database.insert(usersTable)

    fun loginMember(params: Map<String, String>) : LiveData<LoginModel> {
        val liveData = MutableLiveData<LoginModel>()

        retrofitHelper.loginMember(params)?.enqueue(object: Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                if (response.isSuccessful) {
                    liveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                liveData.value = LoginModel(false, "$call", "", null)
            }
        })

        return liveData
    }

}