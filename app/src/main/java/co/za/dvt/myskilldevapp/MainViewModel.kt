package co.za.dvt.myskilldevapp

import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var loginRepo: MainRepo

    init {
        loginRepo = MainRepo()
        Log.i("MV", "ViewModel init...")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MV", "onCleared")
    }
}