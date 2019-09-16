package co.za.dvt.myskilldevapp.features.dashboard

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel

class DashboardViewModel : BaseVieModel() {
    var dashboardRepository: DashboardRepository

    private var message: String
    private var luckyNumber: Int
    private var rolledNumber: Int
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
        dashboardRepository = DashboardRepository()

        _isBusy = MutableLiveData()
        _isWin = MutableLiveData()
        _isError = MutableLiveData()
        luckyNumber = 0
        rolledNumber = 0
        message = "Try your luck... roll the dice"

        countDownTimer = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {

            }
        }.start()

        //dashboardRepository.dashboardModel.observe(this, Observer { })
    }

    fun getLuckyNumber(){
        _isBusy.value = true
        message = "Rolling..."
        dashboardRepository.fetchLuckyNumberFromRepo()
    }

    fun onLuckyNumberRetrieved() {
        rolledNumber = (1..6).random()
        message = "You rolled a $rolledNumber please try again"
        _isWin.value = luckyNumber == rolledNumber
    }

    fun onConnectionError() {
        _isError.value = true
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MV", "onCleared")
    }

    fun resetGame(){
        message = "$luckyNumber is your lucky number you've won this round... please roll again to win more"
        // isBusy.value = false
        // isWin.value = false
        // isError.value = false
    }
}