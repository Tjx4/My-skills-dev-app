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
    var testMessage: MutableLiveData<String> = MutableLiveData()

    var username: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    var currentUserMessage: MutableLiveData<String> = MutableLiveData()

    init {
        testMessage.value = "Enter your details to access your account"

        ioScope.launch {
            var lastUser = loginRepository.getLastUser()

            uiScope.launch {
                if(lastUser != null){
                    showPreloadedUser.value = true
                    currentUserMessage.value = "Hi ${lastUser.name}"
                    username.value = lastUser.name
                }
            }
        }

    }

    fun showPreviousUsers(){
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

            uiScope.launch {
                testMessage.value  = "Cycle done, restarting in 3secs.."
                _showContent.value = true
            }

            delay(1000)
            uiScope.launch {
                testMessage.value  = "Cycle done, restarting in 2secs.."
            }

            delay(1000)
            uiScope.launch {
                testMessage.value  = "Cycle done, restarting in 1secs.."
            }

            delay(1000)

            var usersTable = UsersTable()
            usersTable.name = username.value
            usersTable.surname = password.value
            usersTable.picUrl = "http//"
            loginRepository.addUserToDb(usersTable)

            uiScope.launch {
                testMessage.value = "username: ${username.value} / Password: ${password.value}"
                password.value = ""

            }

        }

    }


}