package co.za.dvt.myskilldevapp.features.dashboard

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.constants.ATMT
import co.za.dvt.myskilldevapp.features.dashboard.database.GameStats
import co.za.dvt.myskilldevapp.features.dashboard.database.GameStatsDAO
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.models.CarModel
import kotlinx.coroutines.*
import java.util.HashMap
import java.util.concurrent.TimeUnit

class DashboardViewModel(private val dashboardRepository: DashboardRepository, private val database: GameStatsDAO, application: Application) : BaseVieModel(application) {

    var winCount: Int = 0
    var tries: Int = 0
    var busyMessage: String = ""

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private var gameStats: MutableLiveData<GameStats?> = MutableLiveData()
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
    val currentLuckyNumber: LiveData<Int>
    get() = _currentLuckyNumber

    private val _rolledNumber: MutableLiveData<Int> = MutableLiveData()
    val rolledNumber: LiveData<Int>
    get() = _rolledNumber

    private val _isBusy: MutableLiveData<Boolean> = MutableLiveData()
    val isBusy: LiveData<Boolean>
    get() = _isBusy

    private val _isLuckyNumberError: MutableLiveData<Boolean> = MutableLiveData()
    val isLuckyNumberError: LiveData<Boolean>
    get() = _isLuckyNumberError

    private val _isCarsError: MutableLiveData<Boolean> = MutableLiveData()
    val isCarsError: LiveData<Boolean>
    get() = _isCarsError

    private val _isTimeFinished: MutableLiveData<Boolean> = MutableLiveData()
    val isTimeFinished: LiveData<Boolean>
    get() = _isTimeFinished

    private val _isWin: MutableLiveData<Boolean> = MutableLiveData()
    val isWin: LiveData<Boolean>
    get() = _isWin

    private val _timeLeft: MutableLiveData<String> = MutableLiveData()
    val timeLeft: LiveData<String>
    get() = _timeLeft

    init {
        fullGameTime = 60000
        remainingGameTime = fullGameTime
        startNewRound()
    }

    fun startNewRound() {
        busyMessage = app.getString(R.string.start_round_message)
        _isBusy.value = true

        ioScope.launch {
            val payload = HashMap<String, String>()
            payload[ATMT] = "0"
            val round = dashboardRepository.fetchLuckyNumber(payload)

            CoroutineScope(Dispatchers.Main).launch {

                if(round != null){
                    _activityMessage.value = app.getString(R.string.try_your_luck_roll_dice)
                    _currentLuckyNumber.value = round.luckyNumber
                    startCountDown(remainingGameTime)
                }
                else{
                    _isLuckyNumberError.value = true
                }

                _isBusy.value = false
            }
        }
    }

    fun fetchCarPrices() {
        busyMessage = app.getString(R.string.fetching_prices)
        _isBusy.value = true

        ioScope.launch {

            val cars = dashboardRepository.fetchAvailableCars()

            CoroutineScope(Dispatchers.Main).launch {

                if(cars != null) {
                    _availableCars.value = cars
                }
                else{
                    _isCarsError.value = true
                }

                _isBusy.value = false
            }
        }

    }

    fun pauseCountDown(){
       _countDownTimer?.cancel()
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

    fun onRollCompleted() {
        _rolledNumber.value  = (1..6).random()
        _activityMessage.value  = "You rolled a ${_rolledNumber.value} please try again"
        val win = _currentLuckyNumber.value == _rolledNumber.value

        //Todo: finish
        if(win){
            _isWin.value = win
        }

if(tries < 1){
    initStats()
    startTracking()
}

        ++tries
    }

  fun getRolledNumberDiceResource(rolledNumber: Int): Int {
        return when(rolledNumber){
            1 -> R.drawable.ic_di_1
            2 -> R.drawable.ic_di_2
            3 -> R.drawable.ic_di_3
            4 -> R.drawable.ic_di_4
            5 -> R.drawable.ic_di_5
            else -> R.drawable.ic_di_6
        }
    }

  fun resetGame(){
        startNewRound()
        _isWin.value = false
        _isLuckyNumberError.value = false
    }

    fun incrimentWin() {
        ++winCount
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        uiScope.launch {
            clear()
            gameStats.value = null
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

    fun initStats(){
        uiScope.launch {
            gameStats.value = getCurrentStatsFromDB()
        }
    }

    fun startTracking(){
        uiScope.launch {
            var currentStats = GameStats()
            currentStats.player = 1
            insert(currentStats)

            gameStats.value = getCurrentStatsFromDB()
        }
    }

    fun stopTracking(){
        uiScope.launch {
            var oldStats = gameStats.value ?: return@launch
            oldStats.endTime = System.currentTimeMillis()
            oldStats.tries = tries
            update(oldStats)
        }
    }

    suspend fun getCurrentStatsFromDB(): GameStats{
        return withContext(Dispatchers.IO){
            var stats = database.getCurrentStats()

            if(stats?.endTime != stats?.startTime){
                null
            }

            stats
        }
    }

    fun setJackpotPrice(jackpotPrice: String) {
        gameStats.value?.jackpotPrice = jackpotPrice
        stopTracking()
    }

    suspend fun insert(currentStats: GameStats) {
        withContext(Dispatchers.IO){
            database.insert(currentStats)
        }
    }

    suspend fun update(oldStats: GameStats) {
        withContext(Dispatchers.IO){
            database.update(oldStats)
        }
    }

    suspend fun getAllStatsFromDB(): List<GameStats>?{
        return withContext(Dispatchers.IO){
            var stats = database.getAllGameStats()
            stats
        }
    }
}