package co.za.dvt.myskilldevapp.features.dashboard

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.models.Car
import co.za.dvt.myskilldevapp.models.LuckyNumberModel

class DashboardViewModel : BaseVieModel() {
    private var dashboardRepository: DashboardRepository = DashboardRepository()

    private val _luckyNumberModel: MutableLiveData<LuckyNumberModel>
    val luckyNumberModel: LiveData<LuckyNumberModel>
    get() = _luckyNumberModel

    private val _availableCars: MutableLiveData<List<Car>>
    val availableCars: LiveData<List<Car>>
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

    private var timeLeft: String
    private var countDownTimer: CountDownTimer

    private val _isBusy: MutableLiveData<Boolean>
    val isBusy: LiveData<Boolean>
    get() = _isBusy

    private val _isError: MutableLiveData<Boolean>
    val isError: LiveData<Boolean>
    get() = _isError

    private val _isWin: MutableLiveData<Boolean>
    val isWin: LiveData<Boolean>
    get() = _isWin

    init {
        _luckyNumberModel = dashboardRepository.luckyNumberModel
        _availableCars = dashboardRepository.availableCars
        _isBusy = MutableLiveData()
        _isWin = MutableLiveData()
        _isError = MutableLiveData()
        _luckyNumber = MutableLiveData()
        _rolledNumber = MutableLiveData()

        _message = MutableLiveData()
        _message.value = "Try your luck... roll the dice"

        timeLeft = "0:00"
        countDownTimer = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toString()
            }

            override fun onFinish() {

            }
        }.start()

        fetchLuckyNumber()
    }

    fun rollDice(){
        _message.value = "Rolling..."
    }

    fun setLuckyNumber() {
        _luckyNumber.value = _luckyNumberModel?.value?.luckyNumber ?: 0
        _isBusy.value = false
        _isError.value = false
    }

    private fun fetchLuckyNumber() {
        _isBusy.value = true
        dashboardRepository.fetchLuckyNumber()
    }

    fun onConnectionError() {
        _isBusy.value = false
        _isError.value = true
    }

    fun onRollCompleted() {
        _rolledNumber.value  = (1..6).random()
        _message.value  = "You rolled a ${_rolledNumber.value} please try again"
        _isWin.value = _luckyNumber.value == _rolledNumber.value
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

    override fun onCleared() {
        super.onCleared()
        Log.i("MV", "onCleared")
    }

    fun resetGame(){
        fetchLuckyNumber()
        _isWin.value = false
        _isError.value = false
    }

}