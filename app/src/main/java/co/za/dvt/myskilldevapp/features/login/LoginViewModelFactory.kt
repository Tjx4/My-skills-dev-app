package co.za.dvt.myskilldevapp.features.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class LoginViewModelFactory (private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            val loginRepository = LoginRepository(application)
            return LoginViewModel(application, loginRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}