package co.za.dvt.myskilldevapp

import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel : ViewModel() {
    var loginRepo: MainRepo
    var message: String
    var luckyNumber: Int = 0
    var rolledNumber: Int = 0

    init {
        loginRepo = MainRepo()
        message = "Try your luck... roll the dice"

        Log.i("MV", "ViewModel init...")
    }

    fun getLuckyNumber(){
        message = "Rolling..."
    }

    fun onLuckyNumberRetrieved() {
        luckyNumber = 2
        rolledNumber = (1..6).random()
        message = "You rolled a 1"
    }

    override fun onCleared() {
        super.onCleared()

        Log.i("MV", "onCleared")
    }
}