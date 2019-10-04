package co.za.dvt.myskilldevapp.features.dashboard

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.features.dashboard.database.GameStats
import co.za.dvt.myskilldevapp.features.dashboard.database.GameStatsDAO
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.models.Car
import co.za.dvt.myskilldevapp.models.LuckyNumber
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class DashboardViewModel(private val dashboardRepository: DashboardRepository, private val database: GameStatsDAO, application: Application) : BaseVieModel(application) {

    var winCount: Int = 0
    var tries: Int = 0

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var gameStats: MutableLiveData<GameStats?>

    private val _countDownTimer: CountDownTimer
    val countDownTimer: CountDownTimer
    get() = _countDownTimer

    private val _luckyNumberModel: MutableLiveData<LuckyNumber?>
    val luckyNumberModel: LiveData<LuckyNumber?>
    get() = _luckyNumberModel

    private val _availableCars: MutableLiveData<List<Car>?>
    val availableCars: LiveData<List<Car>?>
    get() = _availableCars

    private val _message: MutableLiveData<String>
    val message: LiveData<String>
    get() = _message

    private val _luckyNumber: MutableLiveData<Int>
    val currentLuckyNumber: LiveData<Int>
    get() = _luckyNumber

    private val _rolledNumber: MutableLiveData<Int>
    val rolledNumber: LiveData<Int>
    get() = _rolledNumber

    private val _isBusy: MutableLiveData<Boolean>
    val isBusy: LiveData<Boolean>
    get() = _isBusy

    private val _isError: MutableLiveData<Boolean>
    val isError: LiveData<Boolean>
    get() = _isError

    private val _isTimeFinished: MutableLiveData<Boolean>
    val isTimeFinished: LiveData<Boolean>
    get() = _isTimeFinished

    private val _isCarsError: MutableLiveData<Boolean>
    val isCarsError: LiveData<Boolean>
    get() = _isCarsError

    private val _isWin: MutableLiveData<Boolean>
    val isWin: LiveData<Boolean>
    get() = _isWin

    private val _timeLeft: MutableLiveData<String>
    val timeLeft: LiveData<String>
    get() = _timeLeft


    init {
        _luckyNumberModel = dashboardRepository.luckyNumber
        _availableCars = dashboardRepository.availableCars

        _isBusy = MutableLiveData()
        _isError = MutableLiveData()
        _isCarsError = MutableLiveData()
        _luckyNumber = MutableLiveData()
        _rolledNumber = MutableLiveData()
        _isWin = MutableLiveData()
        gameStats = MutableLiveData()
        _message = MutableLiveData()
        _timeLeft = MutableLiveData()
        _isTimeFinished = MutableLiveData()

        _countDownTimer = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.value = String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
            }

            override fun onFinish() {
                _isTimeFinished.value = true
            }
        }.start()

        fetchLuckyNumber()
    }

    fun rollDice(){
        _message.value = app.getString(R.string.rolling)
    }

    fun fetchLuckyNumber() {
        _isBusy.value = true
        dashboardRepository.fetchLuckyNumber()
    }

    fun fetchCars() {
        _isBusy.value = true
        dashboardRepository.fetchAvailableCars()
    }

    fun setLuckyNumber(luckyNumber: Int) {
        _luckyNumber.value = luckyNumber
        _isBusy.value = _luckyNumber?.value == 0
        _isError.value = false
    }

    fun setAvailableCars(availableCars: List<Car>) {
        _availableCars.value = availableCars
        _isBusy.value = false
        _isCarsError.value = false
    }

    fun onLuckyNumnerError() {
        _isError.value = true
        _isBusy.value = false
    }

    fun onAvailableCarsError() {
        _isBusy.value = false
        _isCarsError.value = true
    }

    fun resetMessage() {
        _message.value = "Try your luck... roll the dice"
    }

    fun onRollCompleted() {
        _rolledNumber.value  = (1..6).random()
        _message.value  = "You rolled a ${_rolledNumber.value} please try again"
        _isWin.value = _luckyNumber.value == _rolledNumber.value

        if(tries < 1){
            initStats()
            onStartTracking()
        }

        ++tries
    }

    fun getRolledNumberDi(rolledNumber: Int): Int {
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
        fetchLuckyNumber()
        _isWin.value = false
        _isError.value = false
    }

    fun showPrices(){
        fetchCars()
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


    private fun initStats(){
        uiScope.launch {
            gameStats.value = getCurrentStatsFromDB()
        }
    }

    private fun someWorkNeedsToBeDone(){
        uiScope.launch {
            suspendFuntion()
        }
    }

    private suspend fun suspendFuntion(){
        withContext(Dispatchers.IO){
            longRunningWork()
        }
    }

    private suspend fun longRunningWork(){
        withContext(Dispatchers.IO){

        }
    }

    private fun onStartTracking(){
        uiScope.launch {
            var currentStats = GameStats()
            currentStats.player = 1
            insert(currentStats)

            gameStats.value = getCurrentStatsFromDB()
        }
    }

    private suspend fun getCurrentStatsFromDB(): GameStats{
        return withContext(Dispatchers.IO){
            var stats = database.getCurrentStats()

            if(stats?.endTime != stats?.startTime){
                null
            }

            stats
        }
    }

    private suspend fun insert(currentStats: GameStats) {
        withContext(Dispatchers.IO){
            database.insert(currentStats)
        }
    }

    private suspend fun update(oldStats: GameStats) {
        withContext(Dispatchers.IO){
            database.update(oldStats)
        }
    }

    private fun onStopTracking(){
        uiScope.launch {
            var oldStats = gameStats.value ?: return@launch
            oldStats.endTime = System.currentTimeMillis()
            oldStats.tries = tries
            update(oldStats)
        }
    }

    fun setJackpotPrice(jackpotPrice: String) {
        gameStats.value?.jackpotPrice = jackpotPrice
        //Todo: Move to relevant place
        onStopTracking()
    }

    private suspend fun getAllStatsFromDB(): List<GameStats>?{
        return withContext(Dispatchers.IO){
            var stats = database.getAllGameStats()
            stats
        }
    }

}