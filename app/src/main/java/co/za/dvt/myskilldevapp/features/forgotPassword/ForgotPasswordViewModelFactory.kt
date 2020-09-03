package co.za.dvt.myskilldevapp.features.forgotPassword

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.za.dvt.myskilldevapp.helpers.API
import java.lang.IllegalArgumentException

class ForgotPasswordViewModelFactory (private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java)){
            val retrofitHelper = API.retrofitHelper
            val rorgotPasswordRepository = ForgotPasswordRepository(retrofitHelper)
            return ForgotPasswordViewModel(application, rorgotPasswordRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}