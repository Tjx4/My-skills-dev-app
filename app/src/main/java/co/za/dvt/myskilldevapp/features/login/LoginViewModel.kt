package co.za.dvt.myskilldevapp.features.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.extensions.isValidPassword
import co.za.dvt.myskilldevapp.extensions.isValidUsername
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.models.LoginModel
import co.za.dvt.myskilldevapp.models.UserModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(application: Application, private val loginRepository: LoginRepository) : BaseVieModel(application) {

    private val _currentUser: MutableLiveData<UserModel> = MutableLiveData()
    val currentUser: MutableLiveData<UserModel>
        get() = _currentUser

    private val _showPreloadedUser: MutableLiveData<Boolean> = MutableLiveData()
    val showPreloadedUser: MutableLiveData<Boolean>
        get() = _showPreloadedUser

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _isValidDetails: MutableLiveData<Boolean> = MutableLiveData()
    val isValidDetails: MutableLiveData<Boolean>
        get() = _isValidDetails

    private val _showContent: MutableLiveData<Boolean> = MutableLiveData()
    val showContent: MutableLiveData<Boolean>
        get() = _showContent

    var busyMessage: String = "Signing in, please wait.."

    private val _username: MutableLiveData<String> = MutableLiveData()
    var username: MutableLiveData<String> = MutableLiveData()
    get() = _username

    private var _password: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    get() = _password

    private var _errorMessage: MutableLiveData<String> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    get() = _errorMessage

    var currentUserMessage: MutableLiveData<String> = MutableLiveData()

    init {
        setManualMode()
        checkAndPresetUser()
    }

    private fun checkAndPresetUser() {
        ioScope.launch {
            var lastUser = loginRepository.getLastCachedUser()

            uiScope.launch {
                if (lastUser != null) {
                    preSetUser(lastUser)
                }
            }
        }
    }

    fun setManualMode() {
        currentUserMessage.value = app.getString(R.string.default_login_message)
    }

    fun preSetUser(user: UserModel) {
        showPreloadedUser.value = true
        currentUserMessage.value = "<b>Hi ${user.name}</b>, please enter your password to continue"
        username.value = user.username
    }

    suspend fun getUsers() = loginRepository.getAllCachedUsers()

    fun checkAndSignIn(){
        if(!checkIsValidUsername(_username.value)){
            errorMessage.value = "Please enter a username or email"
            return
        }

        if(!checkIsValidPassword(_username.value)){
            errorMessage.value = "Please enter a valid password"
            return
        }

        _showLoading.value = true

        ioScope.launch {

// Todo: remove delay
delay(1000)

            uiScope.launch {
                _isValidDetails.value = true
            }
        }

    }

    fun signIn() : LiveData<LoginModel> {
        var params = mutableMapOf<String, String>()
        params["username"] = _username.value ?: ""
        params["password"] = _password.value ?: ""

        return loginRepository.loginMember(params)
    }

    fun onSigninCalled(login: LoginModel){
        if(login.success){
            _currentUser.value = login.user

            ioScope.launch {
                var isUserSaved = false

                var previousUsers = getUsers()

                previousUsers?.forEach {
                    isUserSaved = login.user?.email == it.email
                }

                if(!isUserSaved){
                    addUserToDb(login?.user!!)
                }
            }

        }
        else{
            _showContent.value = true
            _password.value = ""
            errorMessage.value = app.getString(R.string.login_error)
        }
    }
    
    suspend fun addUserToDb(user: UserModel) {
        loginRepository.addUserToDb(user)
    }

    fun checkIsValidUsername(username: String?): Boolean {
        return username?.isValidUsername() ?: false
    }

    fun checkIsValidPassword(password: String?): Boolean {
        return password?.isValidPassword() ?: false
    }

}