package co.za.dvt.myskilldevapp.features.registration

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.enums.Gender
import co.za.dvt.myskilldevapp.enums.UserTypes
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel

class RegistrationViewModel(application: Application) : BaseVieModel(application) {
    // UserType = Personal/basic info = Varify info
    private val registrationRepository: RegistrationRepository = RegistrationRepository(application)

    private var _userType: MutableLiveData<UserTypes> = MutableLiveData()
    val userType: LiveData<UserTypes>
    get() = _userType

    private var _name: MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String>
    get() = _name

    private var _surname: MutableLiveData<String> = MutableLiveData()
    val surname: LiveData<String>
    get() = _surname

    private var _gender: MutableLiveData<Gender> = MutableLiveData()
    val gender: LiveData<Gender>
    get() = _gender

    private var _email: MutableLiveData<String> = MutableLiveData()
    val email: LiveData<String>
        get() = _email

    private var _mobileNumber: MutableLiveData<Int> = MutableLiveData()
    val mobileNumber: LiveData<Int>
        get() = _mobileNumber

    private var _password: MutableLiveData<String> = MutableLiveData()
    val password: LiveData<String>
        get() = _password

    private var _confirmPassword: MutableLiveData<String> = MutableLiveData()
    val confirmPassword: LiveData<String>
        get() = _confirmPassword

    init {
        _name.value = "Tshepo"
    }

    fun setUserType(userType: UserTypes){
        _userType.value = userType
    }
}