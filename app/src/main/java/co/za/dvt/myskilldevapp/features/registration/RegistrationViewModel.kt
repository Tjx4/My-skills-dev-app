package co.za.dvt.myskilldevapp.features.registration

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.enums.Gender
import co.za.dvt.myskilldevapp.enums.UserTypes
import co.za.dvt.myskilldevapp.extensions.*
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.models.RegistrationModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegistrationViewModel(application: Application, var registrationRepository: RegistrationRepository) : BaseVieModel(application) {
    // UserType = Personal/basic info = Varify info

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private var _userType: MutableLiveData<UserTypes> = MutableLiveData()
    val userType: LiveData<UserTypes>
    get() = _userType

    private var _username: MutableLiveData<String> = MutableLiveData()
    val username: LiveData<String>
    get() = _username

    private var _name: MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String>
    get() = _name

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

    private var _mobileNumber: MutableLiveData<String> = MutableLiveData()
    val mobileNumber: LiveData<String>
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

    private val _isRegistered: MutableLiveData<Boolean> = MutableLiveData()
    val isRegistered: MutableLiveData<Boolean>
        get() = _isRegistered

    var busyMessage: String = "Creating account, please wait.."

    init {
        _name.value = "Boby"
        _surname.value = "Green"
        _email.value = "test@email.com"
        _mobileNumber.value = "0829954990"
        _gender.value = Gender.Male
        _password.value = "Bl@12345"
        _confirmPassword.value = "Bl@12345"
    }

    fun setUserType(userType: UserTypes){
        _userType.value = userType
    }

    fun setNames(){
        _fullNames.value = "${_name.value} ${_surname.value ?: ""}"
    }

    fun setGender(gender: Gender){
        _gender.value = gender
    }

    fun checkAndRegisterUser() {
/*
        if(!checkIsValidUsername(_username.value)){
            _errorMessage.value = "Please enter a username or email"
            return
        }
*/
        if(!checkIsValidName(_name.value)){
            _errorMessage.value = app.getString(R.string.name_validation)
            return
        }

        if(!checkIsValidSurname(_surname.value)){
            _errorMessage.value = "Please enter a valid surname"
            return
        }

        if(!checkIsValidEmail(_email.value)){
            _errorMessage.value = "Please enter a valid email"
            return
        }

        if(!checkIsValidMobile(_mobileNumber.value)){
            _errorMessage.value = "Please enter a valid mobile number"
            return
        }

        if(!checkIsValidPassword(_password.value)){
            _errorMessage.value = "Please enter a valid password"
            return
        }

        if(!checkIsPasswordsMatch(_password.value, _confirmPassword.value)){
            _errorMessage.value = "Please confirm your passwords"
            return
        }

        _showLoading.value = true

        ioScope.launch {
            var registration = registerUser()

// Todo: remove delay
delay(1000)

            uiScope.launch {

                if(registration.success){
                    _isRegistered.value = true
                }
                else{
                    _errorMessage.value = app.getString(R.string.login_error)
                }
            }
        }
    }

    suspend fun registerUser() : RegistrationModel {
        var params = mutableMapOf<String, String>()
        params["username"] = _username.value ?: ""
        params["name"] = _name.value ?: ""
        params["surname"] = _surname.value ?: ""
        params["password"] = _password.value ?: ""
        params["gender"] = "${_gender.value?.id ?: 0}"

        return registrationRepository.registerUser(params)
    }

    fun checkIsValidUsername(username: String?): Boolean {
        return username?.isValidUsername() ?: false
    }

    fun checkIsValidName(name: String?): Boolean {
        return name?.isValidName() ?: false
    }

    fun checkIsValidSurname(surname: String?): Boolean {
        return surname?.isValidName() ?: false
    }

    fun checkIsValidEmail(email: String?): Boolean {
        return email?.isValidEmail() ?: false
    }

    fun checkIsValidMobile(mobile: String?): Boolean {
        return mobile?.isValidMobileNumber() ?: false
    }

    fun checkIsValidPassword(password: String?): Boolean {
        return password?.isValidPassword() ?: false
    }

    fun checkIsPasswordsMatch(password: String?, confirmPassword: String?): Boolean {
        return password?.isMatchPasswords(confirmPassword) ?: false
    }
}