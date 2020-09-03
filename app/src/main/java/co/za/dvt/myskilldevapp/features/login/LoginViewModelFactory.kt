package co.za.dvt.myskilldevapp.features.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.za.dvt.myskilldevapp.features.database.PADatabase
import co.za.dvt.myskilldevapp.helpers.API
import java.lang.IllegalArgumentException

class LoginViewModelFactory (private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            var database = PADatabase.getInstance(application)
            val retrofitHelper = API.retrofitHelper
            val loginRepository = LoginRepository(database, retrofitHelper)
            return LoginViewModel(application, loginRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}