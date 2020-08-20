package co.za.dvt.myskilldevapp.features.registration

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.enums.UserTypes
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel

class RegistrationViewModel(application: Application) : BaseVieModel(application) {
    // UserType = Personal/basic info = Varify info
    private val registrationRepository: RegistrationRepository = RegistrationRepository(application)

    private var _userType: MutableLiveData<UserTypes> = MutableLiveData()
    val userType: LiveData<UserTypes>
    get() = _userType

    fun setUserType(userType: UserTypes){
        _userType.value = userType
    }
}