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
                else{

                }
            }
        }
    }

    fun setManualMode() {
        currentUserMessage.value = app.getString(R.string.default_login_message)
    }

    fun preSetUser(lastUser: UsersTable) {
        showPreloadedUser.value = true
        currentUserMessage.value = "<b>Hi ${lastUser.name}</b>, please enter your password to continue"
        username.value = lastUser.username
    }

    suspend fun getUsers() = loginRepository.getAllCachedUsers()

    fun checkAndSignIn(){
        var username = _username.value ?: ""
        var password = _password.value ?: ""

        if(!checkIsValidUsername(username)){
            errorMessage.value = "Please enter a username or email"
            return
        }

        if(!checkIsValidPassword(password)){
            errorMessage.value = "Please enter a valid password"
            return
        }

        _showLoading.value = true

// Todo: remove delay
        ioScope.launch {
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

            var isUserSaved = false

            ioScope.launch {
                var previousUsers = getUsers()

                previousUsers?.forEach {
                    if (login.user?.email == it.email){
                        isUserSaved = true
                    }
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
    
    suspend fun addUserToDb(userModel: UserModel) {
        var usersTable = UsersTable()
        usersTable.username = userModel.username
        usersTable.name = userModel.name
        usersTable.surname = userModel.name
        usersTable.email = userModel.email
        usersTable.mobile = userModel.mobile
        usersTable.picUrl = userModel.picUrl
        loginRepository.addUserToDb(usersTable)
    }

    fun checkIsValidUsername(username: String): Boolean {
        return username.isValidUsername()
    }

    fun checkIsValidPassword(password: String): Boolean {
        return password.isValidPassword()
    }

}