package co.za.dvt.myskilldevapp.features.forgotPassword

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.za.dvt.myskilldevapp.features.database.PADatabase
import co.za.dvt.myskilldevapp.features.registration.RegistrationRepository
import co.za.dvt.myskilldevapp.helpers.API
import java.lang.IllegalArgumentException

class ForgotPasswordViewModelFactory (private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java)){
            var database = PADatabase.getInstance(application)
            val retrofitHelper = API.retrofitHelper
            val registrationRepository = RegistrationRepository(retrofitHelper)
            return ForgotPasswordViewModel(application, registrationRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}