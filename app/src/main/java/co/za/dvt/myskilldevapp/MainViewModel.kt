package co.za.dvt.myskilldevapp

import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var loginRepo: MainRepo
    var message: String

    init {
        loginRepo = MainRepo()
        message = "Try your luck... roll the dice"

        Log.i("MV", "ViewModel init...")
    }

    fun getLuckyNumber(){
        message = "Rolling..."
    }

    fun onLuckyNumberRetrieved() {
        message = "You rolled a 1"
    }

    override fun onCleared() {
        super.onCleared()

        Log.i("MV", "onCleared")
    }
}