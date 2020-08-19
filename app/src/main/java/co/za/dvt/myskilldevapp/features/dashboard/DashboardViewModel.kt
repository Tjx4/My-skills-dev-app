package co.za.dvt.myskilldevapp.features.dashboard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.features.database.MyGameDatabase
import co.za.dvt.myskilldevapp.features.database.tables.GameStats
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.helpers.getYearMonthDayAndTime
import co.za.dvt.myskilldevapp.models.CarModel
import kotlinx.coroutines.*

class DashboardViewModel(application: Application) : BaseVieModel(application) {
    var dataSource = MyGameDatabase.getInstance(application).gameStatsDAO
    private val dashboardRepository: DashboardRepository = DashboardRepository(dataSource)

    private var gameStats: GameStats = GameStats()

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _showContent: MutableLiveData<Boolean> = MutableLiveData()
    val showContent: MutableLiveData<Boolean>
        get() = _showContent

    var busyMessage: String = ""

    private val _availableCars: MutableLiveData<List<CarModel>> = MutableLiveData()
    val availableCars: MutableLiveData<List<CarModel>>
    get() = _availableCars

    init {
        _availableCars.value = ArrayList<CarModel>()
    }

    fun testFetchSomethingFromAPI(){
        _showLoading.value = true

        ioScope.launch {
           delay(2000)

            uiScope.launch {


                _showContent.value = true
            }
        }


    }

    fun recordGameStats(jackpotPrice: String) {
        gameStats.jackpotPrice = jackpotPrice
        gameStats.player = app.getString(R.string.test_player)
        gameStats.tries = 0
        gameStats.endTime = getYearMonthDayAndTime()

        ioScope.launch {
            dashboardRepository.addStatsToDB(gameStats)
        }
    }

    suspend fun getUserInfo(): List<GameStats>? {
       return withContext(Dispatchers.IO){
            dashboardRepository.getAllStatsFromDB()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}