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

    private val _availableCars: MutableLiveData<List<CarModel>> = MutableLiveData()
    val availableCars: MutableLiveData<List<CarModel>>
    get() = _availableCars

    private val _activityMessage: MutableLiveData<String> = MutableLiveData()
    val activityMessage: MutableLiveData<String>
    get() = _activityMessage

    private val _currentLuckyNumber: MutableLiveData<Int> = MutableLiveData()
    val currentLuckyNumber: MutableLiveData<Int>
    get() = _currentLuckyNumber

    private val _rolledNumber: MutableLiveData<Int> = MutableLiveData()
    val rolledNumber: MutableLiveData<Int>
    get() = _rolledNumber

    private val _isBusy: MutableLiveData<Boolean> = MutableLiveData()
    val isBusy: MutableLiveData<Boolean>
    get() = _isBusy

    private val _luckyNumberError: MutableLiveData<String> = MutableLiveData()
    val luckyNumberError: LiveData<String>
    get() = _luckyNumberError

    private val _carsError: MutableLiveData<String> = MutableLiveData()
    val carsError: LiveData<String>
    get() = _carsError

    private val _isTimeFinished: MutableLiveData<Boolean> = MutableLiveData()
    val isTimeFinished: LiveData<Boolean>
    get() = _isTimeFinished

    private val _winCount: MutableLiveData<Int> = MutableLiveData()
    val winCount: LiveData<Int>
    get() = _winCount

    private val _timeLeft: MutableLiveData<String> = MutableLiveData()
    val timeLeft: LiveData<String>
    get() = _timeLeft

    private val _round: MutableLiveData<Int> = MutableLiveData()
    val round: LiveData<Int>
    get() = _round

    init {
        startGame()
    }

    private fun startGame(){
        gameStats.startTime = getYearMonthDayAndTime()
        resetGame()
    }

    private fun initGame() {
        fullGameTime = 60000
        remainingGameTime = fullGameTime
// _winCount.value = 0
    }

    fun startNewRound() {
        busyMessage = app?.getString(R.string.start_round_message) ?: ""
        _isBusy.value = true

        _round.value.let {
            var lastRound = it ?: 0
            _round.value = lastRound + 1
        }

        ioScope.launch {
            val token = "dfd9d0d0j99je9999e9999e9j9"

            val payload = HashMap<String, String>()
            payload[USER] = app.getString(R.string.test_player)

            val round = getLuckNumber(token, payload)

            uiScope.launch {
                _isBusy.value = false

                if(round != null) {
                    _activityMessage.value = app.getString(R.string.try_your_luck_roll_dice)
                    _currentLuckyNumber.value = round.luckyNumber
                    continueCountDown()
                }
                else{
                    _luckyNumberError.value = app.getString(R.string.lucky_number_error_message)
                }
            }
        }
    }

    suspend fun getLuckNumber(token: String, payload: HashMap<String, String>) = dashboardRepository.fetchLuckyNumber(token, payload)

    fun fetchAndSetJackportPrices() {
        busyMessage = app?.getString(R.string.fetching_prices) ?: ""
        _isBusy.value = true

        ioScope.launch {

            val cars = getJackportPrices()

            CoroutineScope(Dispatchers.Main).launch {

                _isBusy.value = false

                if(cars != null) {
                    _availableCars.value = cars
                }
                else{
                    _carsError.value = app.getString(R.string.cars_error_message)
                }
            }
        }
    }

    suspend fun getJackportPrices() = dashboardRepository.fetchAvailableCars()

    fun pauseCountDown(){
       _countDownTimer?.cancel()
    }

    fun continueCountDown(){
        startCountDown(remainingGameTime)
    }

    fun startCountDown(gameTime: Long){
        _countDownTimer = object : CountDownTimer(gameTime, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                remainingGameTime = millisUntilFinished

                _timeLeft.value = String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
            }

            override fun onFinish() {
                _isTimeFinished.value = true
            }
        }

        _countDownTimer?.start()
    }

    fun onRollCompleted(rolledNumber: Int) {
        _rolledNumber.value  = rolledNumber
        _activityMessage.value  =  app.getString(R.string.rolled_message, _rolledNumber.value)

        //Todo: finish
        val win = _currentLuckyNumber.value == _rolledNumber.value

        if(win){
            _winCount.value.let {
                var lastCount = it ?: 0
                _winCount.value = lastCount + 1
            }
        }

        ++tries
    }

    fun resetGame(){
        initGame()
        startNewRound()
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