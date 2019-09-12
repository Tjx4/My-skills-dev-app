package co.za.dvt.myskilldevapp

import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var loginRepo: MainRepo
    var message: String
    var luckyNumber: Int
    var rolledNumber: Int
    var isWin: Boolean = false

    init {
        loginRepo = MainRepo()
        luckyNumber = 0
        rolledNumber = 0
        message = "Try your luck... roll the dice"

        Log.i("MV", "ViewModel init...")
    }

    fun getLuckyNumber(){
        message = "Rolling..."
    }

    fun setNewLuckyNumber(){
        luckyNumber = (1..6).random()
    }

    fun onLuckyNumberRetrieved() {
        rolledNumber = (1..6).random()
        message = "You rolled a $rolledNumber"
        isWin = luckyNumber == rolledNumber
    }

    override fun onCleared() {
        super.onCleared()

        Log.i("MV", "onCleared")
    }

    fun resetGame(){
        setNewLuckyNumber()
        message = "You've won this round... please roll the again to win more"
    }
}