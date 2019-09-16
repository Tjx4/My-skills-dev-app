package co.za.dvt.myskilldevapp.features.dashboard

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel

class DashboardViewModel : BaseVieModel() {
    private val dashboardRepository: DashboardRepository

    private var _message: String
    var message: String? = null
    get() = _message

    private var _luckyNumber: Int
    var luckyNumber: Int = 0
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
        dashboardRepository = DashboardRepository()

        _isBusy = MutableLiveData()
        _isWin = MutableLiveData()
        _isError = MutableLiveData()
        _luckyNumber = 0
        _rolledNumber = MutableLiveData()
        _message = "Try your luck... roll the dice"

        timeLeft = "0:00"
        countDownTimer = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toString()
            }

            override fun onFinish() {

            }
        }.start()

        //dashboardRepository.dashboardModel.observe(this, Observer { })
    }

    fun getLuckyNumber(){
        _isBusy.value = true
        _message = "Rolling..."
        dashboardRepository.fetchLuckyNumberFromRepo()
    }

    fun onLuckyNumberRetrieved() {
        _rolledNumber.value  = (1..6).random()
        _message = "You rolled a ${_rolledNumber.value}  please try again"
        _isWin.value = _luckyNumber == _rolledNumber.value
    }

    fun setRolledNumberDi() {
        var diceImageRes = when(_rolledNumber.value){
            1 -> R.mipmap.ic_dice_1
            2 -> R.mipmap.ic_dice_2
            3 -> R.mipmap.ic_dice_3
            4 -> R.mipmap.ic_dice_4
            5 -> R.mipmap.ic_dice_5
            else -> R.mipmap.ic_dice_6
        }
    }

    fun onConnectionError() {
        _isError.value = true
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MV", "onCleared")
    }

    fun resetGame(){
        _message = "$_luckyNumber is your lucky number you've won this round... please roll again to win more"
        // isBusy.value = false
        // isWin.value = false
        // isError.value = false
    }
}