package co.za.dvt.myskilldevapp.features.registration

import android.app.Application
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.enums.Gender
import co.za.dvt.myskilldevapp.enums.UserTypes
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegistrationViewModel(application: Application) : BaseVieModel(application), Observable {
    // UserType = Personal/basic info = Varify info
    private val registrationRepository: RegistrationRepository = RegistrationRepository(application)

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private var _userType: MutableLiveData<UserTypes> = MutableLiveData()
    val userType: LiveData<UserTypes>
    get() = _userType

    private var _userName: MutableLiveData<String> = MutableLiveData()
    val userName: LiveData<String>
    get() = _userName

    //private var _name: MutableLiveData<String> = MutableLiveData()
    //val name: LiveData<String>
    //get() = _name
    @Bindable
    var name: String = ""

    private var _surname: MutableLiveData<String> = MutableLiveData()
    val surname: LiveData<String>
    get() = _surname

    private var _fullNames: MutableLiveData<String> = MutableLiveData()
    open val fullNames: LiveData<String>
    get() = _fullNames

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

    private var _errorMessage: MutableLiveData<String> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
        get() = _errorMessage

    var busyMessage: String = "Creating account, please wait.."

    init {
        name = "Boby"
        _surname.value = "Green"
        _email.value = "test@email.com"
        _mobileNumber.value = 829954990
        _gender.value = Gender.Male
        _password.value = "B@12345"
        _confirmPassword.value = "B@12345"
    }

    fun setUserType(userType: UserTypes){
        _userType.value = userType
    }

    fun setNames(){
        _fullNames.value = "${name} ${_surname.value ?: ""}"
    }

    fun setGender(gender: Gender){
        _gender.value = gender
    }

    fun registerUser() {
        _showLoading.value = true

        ioScope.launch {
            var params = mutableMapOf<String, String>()
            params["name"] = _surname.value ?: ""
            params["password"] = _password.value ?: ""

            var registration = registrationRepository.registerUer(params)

delay(1000)

            uiScope.launch {

                if(registration!!.success){

                }
                else{
                    errorMessage.value = app.getString(R.string.login_error)
                }
            }
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
      var pp = callback.toString()
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
     //   TODO("Not yet implemented")
    }
}