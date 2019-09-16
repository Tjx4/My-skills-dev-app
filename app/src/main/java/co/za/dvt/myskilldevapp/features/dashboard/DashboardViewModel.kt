package co.za.dvt.myskilldevapp.features.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel

class DashboardViewModel : BaseVieModel() {
    var dashboardRepository: DashboardRepository
    var message: String
    var luckyNumber: Int
    var rolledNumber: Int
    val isBusy: MutableLiveData<Boolean>
    val isWin: MutableLiveData<Boolean>
    val isError: MutableLiveData<Boolean>

    init {
        dashboardRepository = DashboardRepository()
        isBusy = MutableLiveData()
        isWin = MutableLiveData()
        isError = MutableLiveData()
        luckyNumber = 0
        rolledNumber = 0
        message = "Try your luck... roll the dice"

        Log.i("MV", "ViewModel init...")
    }

    fun getLuckyNumber(){
        isBusy.value = true
        message = "Rolling..."
        dashboardRepository.getLuckyNumber()
    }

    fun onLuckyNumberRetrieved() {
        rolledNumber = (1..6).random()
        message = "You rolled a $rolledNumber please try again"
        isWin.value = luckyNumber == rolledNumber
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MV", "onCleared")
    }

    fun resetGame(){
        message = "$luckyNumber is your lucky number you've won this round... please roll again to win more"
       // luckyNumber = dashboardRepository.getLuckyNumber()?.luckyNumber
    }
}