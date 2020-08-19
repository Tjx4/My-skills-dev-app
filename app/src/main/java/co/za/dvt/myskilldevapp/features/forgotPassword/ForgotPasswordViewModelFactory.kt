package co.za.dvt.myskilldevapp.features.forgotPassword

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ForgotPasswordViewModelFactory (private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java)){
            return ForgotPasswordViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}