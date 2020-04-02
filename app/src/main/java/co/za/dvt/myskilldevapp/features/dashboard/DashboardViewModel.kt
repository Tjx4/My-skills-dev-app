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
import co.za.dvt.myskilldevapp.models.Car
import kotlinx.coroutines.*
import java.util.HashMap
import java.util.concurrent.TimeUnit

class DashboardViewModel(private val dashboardRepository: DashboardRepository, private val database: GameStatsDAO, application: Application) : BaseVieModel(application) {

    var winCount: Int = 0
    var tries: Int = 0

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    protected val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private var gameStats: MutableLiveData<GameStats?> = MutableLiveData()

    private var _countDownTimer: CountDownTimer? = null
    val countDownTimer: CountDownTimer?
    get() = _countDownTimer

    private val _availableCars: MutableLiveData<List<Car>?> = MutableLiveData()
    val availableCars: LiveData<List<Car>?>
    get() = _availableCars

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: MutableLiveData<String>
    get() = _message

    private val _currentLuckyNumber: MutableLiveData<Int> = MutableLiveData()
    val currentLuckyNumber: LiveData<Int>
    get() = _currentLuckyNumber

    private val _rolledNumber: MutableLiveData<Int> = MutableLiveData()
    val rolledNumber: LiveData<Int>
    get() = _rolledNumber

    private val _isBusy: MutableLiveData<Boolean> = MutableLiveData()
    val isBusy: LiveData<Boolean>
    get() = _isBusy

    private val _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean>
    get() = _isError

    private val _isTimeFinished: MutableLiveData<Boolean> = MutableLiveData()
    val isTimeFinished: LiveData<Boolean>
    get() = _isTimeFinished

    private val _isCarsError: MutableLiveData<Boolean> = MutableLiveData()
    val isCarsError: LiveData<Boolean>
    get() = _isCarsError

    private val _isWin: MutableLiveData<Boolean> = MutableLiveData()
    val isWin: LiveData<Boolean>
    get() = _isWin

    private val _timeLeft: MutableLiveData<String> = MutableLiveData()
    val timeLeft: LiveData<String>
    get() = _timeLeft

    init {
        fetchLuckyNumber()
    }

    fun fetchLuckyNumber() {
        _isBusy.value = true

        ioScope.launch {

            val payload = HashMap<String, String>()
            payload[ATMT] = "0"
            var round = dashboardRepository.fetchLuckyNumber(payload)

            CoroutineScope(Dispatchers.Main).launch {

                if(round != null){
                    _currentLuckyNumber.value = round.luckyNumber
                }
                else{
                    _isError.value = true
                }

                _isBusy.value = false
            }
        }
    }

    fun fetchCars() {
        ioScope.launch {
            CoroutineScope(Dispatchers.Main).launch {
                _isBusy.value = true
                _availableCars.value = dashboardRepository.fetchAvailableCars()
                _isBusy.value = false

                if(availableCars == null){
                    onAvailableCarsError()
                }

            }
        }

    }

    fun setLuckyNumber(luckyNumber: Int) {

        _isError.value = false
        _message.value = app.getString(R.string.try_your_luck_roll_the_dice)
        _isBusy.value = _currentLuckyNumber?.value == 0

       _countDownTimer ?: startCountDown()
    }

    fun startCountDown(){
        _countDownTimer = object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
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

    fun setAvailableCars(availableCars: List<Car>) {
        _availableCars.value = availableCars
        _isCarsError.value = false
        _isBusy.value = false
    }

    fun onAvailableCarsError() {
        _isCarsError.value = true
        _isBusy.value = false
    }

    fun showRolling() {
        _message.value = app.getString(R.string.rolling)
    }

    fun resetMessage() {
        _message.value = app.getString(R.string.try_your_luck_roll_dice)
    }

    fun onRollCompleted() {
        _rolledNumber.value  = (1..6).random()
        _message.value  = "You rolled a ${_rolledNumber.value} please try again"
        _isWin.value = _currentLuckyNumber.value == _rolledNumber.value

        if(tries < 1){
            initStats()
            onStartTracking()
        }

        ++tries
    }

    fun getRolledNumberDiceImage(rolledNumber: Int): Int {
        return when(rolledNumber){
            1 -> R.drawable.ic_di_1
            2 -> R.drawable.ic_di_2
            3 -> R.drawable.ic_di_3
            4 -> R.drawable.ic_di_4
            5 -> R.drawable.ic_di_5
            else -> R.drawable.ic_di_6
        }
    }

    fun showPrices(){

    }

  fun resetGame(){
        fetchLuckyNumber()
        _isWin.value = false
        _isError.value = false
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

    fun resetPrizes() {
        _availableCars.value = ArrayList()
    }


    fun initStats(){
        uiScope.launch {
            gameStats.value = getCurrentStatsFromDB()
        }
    }

    fun onStartTracking(){
        uiScope.launch {
            var currentStats = GameStats()
            currentStats.player = 1
            insert(currentStats)

            gameStats.value = getCurrentStatsFromDB()
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

    fun onStopTracking(){
        uiScope.launch {
            var oldStats = gameStats.value ?: return@launch
            oldStats.endTime = System.currentTimeMillis()
            oldStats.tries = tries
            update(oldStats)
        }
    }

    fun setJackpotPrice(jackpotPrice: String) {
        gameStats.value?.jackpotPrice = jackpotPrice
        onStopTracking()
    }

    suspend fun getAllStatsFromDB(): List<GameStats>?{
        return withContext(Dispatchers.IO){
            var stats = database.getAllGameStats()
            stats
        }
    }

}