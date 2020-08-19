package co.za.dvt.myskilldevapp.features.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseVieModel(application) {
    private val loginRepository: LoginRepository = LoginRepository(application)

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _showContent: MutableLiveData<Boolean> = MutableLiveData()
    val showContent: MutableLiveData<Boolean>
        get() = _showContent

    var busyMessage: String = "Signing in, please wait.."
    var testMessage: MutableLiveData<String> = MutableLiveData()

    init {
        testMessage.value = "Click test button"
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
            uiScope.launch {
                testMessage.value  = "Start cycle"
            }

        }

    }

}