package co.za.dvt.myskilldevapp.features.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.models.UserModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseVieModel(application) {
    private val loginRepository: LoginRepository = LoginRepository(application)

    private val _currentUser: MutableLiveData<UserModel> = MutableLiveData()
    val currentUser: MutableLiveData<UserModel>
        get() = _currentUser

    private val _showPreloadedUser: MutableLiveData<Boolean> = MutableLiveData()
    val showPreloadedUser: MutableLiveData<Boolean>
        get() = _showPreloadedUser

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _showContent: MutableLiveData<Boolean> = MutableLiveData()
    val showContent: MutableLiveData<Boolean>
        get() = _showContent

    private val _previousUsers: MutableLiveData<List<UsersTable>> = MutableLiveData()
    val previousUsers: MutableLiveData<List<UsersTable>>
        get() = _previousUsers

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
        currentUserMessage.value = "Please enter your details to continue"
    }

    fun preSetUser(lastUser: UsersTable) {
        showPreloadedUser.value = true
        currentUserMessage.value = "<b>Hi ${lastUser.name}</b>, please enter your password to continue"
        username.value = lastUser.username
    }

    suspend fun getUsers() = loginRepository.getAllCachedUsers()

    fun getPreviousUsers(){
        ioScope.launch {
            var allUsers = loginRepository.getAllCachedUsers()

            uiScope.launch {
                if(allUsers != null){
                    _previousUsers.value = allUsers
                }
            }
        }
    }

    fun signIn(){
        _showLoading.value = true

        ioScope.launch {
            var params = mutableMapOf<String, String>()
            params["username"] = _username.value ?: ""
            params["password"] = _password.value ?: ""

            var login = loginRepository.loginMember(params)

delay(1000)

            uiScope.launch {


                if(login!!.success){
                    _currentUser.value = login.user
                }
                else{
                    _showContent.value = true
                    password.value = ""
                    errorMessage.value = app.getString(R.string.login_error)
                }
            }

            if(login!!.success){
                addUserToDb(login?.user!!)
            }
        }

    }

    private suspend fun addUserToDb(userModel: UserModel) {
        var usersTable = UsersTable()
        usersTable.username = userModel.username
        usersTable.name = userModel.name
        usersTable.surname = userModel.name
        usersTable.picUrl = userModel.picUrl
        loginRepository.addUserToDb(usersTable)
    }

}