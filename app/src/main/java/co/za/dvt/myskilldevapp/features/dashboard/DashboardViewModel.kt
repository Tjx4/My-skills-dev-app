package co.za.dvt.myskilldevapp.features.dashboard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.features.database.MyGameDatabase
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.helpers.getYearMonthDayAndTime
import co.za.dvt.myskilldevapp.models.CarModel
import kotlinx.coroutines.*

class DashboardViewModel(application: Application) : BaseVieModel(application) {

    private val dashboardRepository: DashboardRepository = DashboardRepository(application)

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _showContent: MutableLiveData<Boolean> = MutableLiveData()
    val showContent: MutableLiveData<Boolean>
        get() = _showContent

    var busyMessage: String = ""
    var testMessage: MutableLiveData<String> = MutableLiveData()

    private val _availableCars: MutableLiveData<List<CarModel>> = MutableLiveData()
    val availableCars: MutableLiveData<List<CarModel>>
    get() = _availableCars

    init {
        testMessage.value = "Please click the test button "
        _availableCars.value = ArrayList<CarModel>()
    }

    fun testFetchSomethingFromAPI(){
        _showLoading.value = true

        ioScope.launch {
           delay(3000)

            uiScope.launch {
                testMessage.value  = "Cycle done, restarting in 3secs.."
                _showContent.value = true
            }

            delay(3000)

            uiScope.launch {
                testMessage.value  = "Start cycle"
            }

        }

    }

    fun recordGameStats(jackpotPrice: String) {

    }

    suspend fun getUserInfo(): List<UsersTable>? {
       return withContext(Dispatchers.IO){
            dashboardRepository.getAllStatsFromDB()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}