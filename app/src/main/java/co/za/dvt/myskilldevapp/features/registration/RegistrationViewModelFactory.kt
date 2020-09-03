package co.za.dvt.myskilldevapp.features.registration

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.za.dvt.myskilldevapp.helpers.API
import java.lang.IllegalArgumentException

class RegistrationViewModelFactory (private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RegistrationViewModel::class.java)){
            val retrofitHelper = API.retrofitHelper
            val registrationRepository = RegistrationRepository(retrofitHelper)
            return RegistrationViewModel(application, registrationRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}