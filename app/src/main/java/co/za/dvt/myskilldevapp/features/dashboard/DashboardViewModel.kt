package co.za.dvt.myskilldevapp.features.dashboard

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.constants.USER
import co.za.dvt.myskilldevapp.features.database.tables.GameStats
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.helpers.getYearMonthDayAndTime
import co.za.dvt.myskilldevapp.models.CarModel
import kotlinx.coroutines.*
import java.util.HashMap
import java.util.concurrent.TimeUnit

class DashboardViewModel(private val dashboardRepository: DashboardRepository, application: Application) : BaseVieModel(application) {

    var tries: Int = 0
    var maxRounds: Int = 2
    var busyMessage: String = ""

    private var viewModelJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var gameStats: GameStats = GameStats()
    private var fullGameTime: Long = 0
    private var remainingGameTime: Long = 0

    private var _countDownTimer: CountDownTimer? = null
    val countDownTimer: CountDownTimer?
    get() = _countDownTimer

    private val _isBusy: MutableLiveData<Boolean> = MutableLiveData()
    val isBusy: MutableLiveData<Boolean>
        get() = _isBusy

    private val _availableCars: MutableLiveData<List<CarModel>> = MutableLiveData()
    val availableCars: MutableLiveData<List<CarModel>>
    get() = _availableCars

    init {

        _availableCars.value = ArrayList<CarModel>()
    }

    fun recordGameStats(jackpotPrice: String) {
        gameStats.jackpotPrice = jackpotPrice
        gameStats.player = app.getString(R.string.test_player)
        gameStats.tries = tries
        gameStats.endTime = getYearMonthDayAndTime()

        ioScope.launch {
            dashboardRepository.addStatsToDB(gameStats)
        }
    }

    suspend fun getGameStats(): List<GameStats>? {
       return withContext(Dispatchers.IO){
            dashboardRepository.getAllStatsFromDB()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}