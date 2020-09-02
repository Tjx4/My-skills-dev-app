package co.za.dvt.myskilldevapp.features.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.features.database.PADatabase
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import co.za.dvt.myskilldevapp.models.LoginModel
import co.za.dvt.myskilldevapp.models.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class LoginRepository(var application: Application) : BaseRepositories() {
    var database = PADatabase.getInstance(application).USERSDAO

    suspend fun getAllCachedUsers() : List<UserModel>?{
       return try {
           val cachedUsers = database.getAllUsers()
           if(cachedUsers.isNullOrEmpty()){
               null
           }
           else {
               var users = arrayListOf<UserModel>()
               var indx = 0
               cachedUsers?.forEach {
                   users.add(UserModel(it.id.toInt(), it.username, it.name, it.surname, it.email, it.mobile))
                   indx++
               }
               users
           }

       }
       catch (e: Exception){
            null
       }
    }

    suspend fun getLastCachedUser() : UserModel?{
        return try{
            val userRow = database.getLastUser()
            if(userRow != null){
                UserModel(userRow?.id?.toInt() ?: 0, userRow?.username, userRow?.name, userRow?.surname, userRow?.email, userRow?.mobile)
            }
            else{
                null
            }

        }
        catch (e: Exception){
             null
        }
    }

    suspend fun addUserToDb(user: UserModel) {
        val userTable = UsersTable(user?.id.toLong(), user?.username, user?.name, user?.surname, user?.email, user?.mobile)
        database.insert(userTable)
    }

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