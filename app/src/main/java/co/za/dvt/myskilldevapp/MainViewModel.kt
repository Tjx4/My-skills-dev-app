package co.za.dvt.myskilldevapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var loginRepo: MainRepo
    var message: String
    var luckyNumber: Int
    var rolledNumber: Int
    val isWin: MutableLiveData<Boolean>

    init {
        loginRepo = MainRepo()
        isWin = MutableLiveData()
        luckyNumber = 0
        rolledNumber = 0
        message = "Try your luck... roll the dice"

        luckyNumber = loginRepo.getLuckyNumber()?.luckyNumber

        Log.i("MV", "ViewModel init...")
    }

    fun getLuckyNumber(){
        message = "Rolling..."
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
        luckyNumber = loginRepo.getLuckyNumber()?.luckyNumber
    }
}