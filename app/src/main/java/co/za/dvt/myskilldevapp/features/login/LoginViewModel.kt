package co.za.dvt.myskilldevapp.features.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseVieModel(application) {
    private val loginRepository: LoginRepository = LoginRepository(application)

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

    var username: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    var currentUserMessage: MutableLiveData<String> = MutableLiveData()

    init {
        setManualMode()
        checkAndPresetUser()
    }

    private fun checkAndPresetUser() {
        ioScope.launch {
            var lastUser = loginRepository.getLastUser()

            uiScope.launch {
                if (lastUser != null) {
                    preSetUser(lastUser)
                }
            }
        }
    }

    fun setManualMode() {
        currentUserMessage.value = "Please enter your details to continue"
    }

    fun preSetUser(lastUser: UsersTable) {
        showPreloadedUser.value = true
        currentUserMessage.value = "<b>Hi ${lastUser.name}</b>, please enter your details to continue"
        username.value = lastUser.name
    }

    suspend fun getUsers() = loginRepository.getAllUsers()

    fun getPreviousUsers(){
        ioScope.launch {
            var allUsers = loginRepository.getAllUsers()

            uiScope.launch {
                if(allUsers != null){
                    _previousUsers.value = allUsers
                }
            }
        }
    }

    fun testFetchSomethingFromAPI(){
        _showLoading.value = true

        ioScope.launch {
            delay(3000)

            var usersTable = UsersTable()
            usersTable.name = username.value
            usersTable.surname = password.value
            usersTable.picUrl = "http//"

            // Todo: Move to after succesfull signin
            // loginRepository.addUserToDb(usersTable)

            uiScope.launch {
                currentUserMessage.value = "${usersTable.name} you are logged in"
                _showContent.value = true
                password.value = ""
            }

        }

    }


}