package co.za.dvt.myskilldevapp.features.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.constants.ATMT
import co.za.dvt.myskilldevapp.features.ViewModels.BaseVieModel
import co.za.dvt.myskilldevapp.models.Roll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class DashboardViewModel : BaseVieModel() {
    var loginRepo: DashboardRepository
    var message: String
    var luckyNumber: Int
    var rolledNumber: Int
    val isBusy: MutableLiveData<Boolean>
    val isWin: MutableLiveData<Boolean>
    val isError: MutableLiveData<Boolean>
    var atmpt: Int

    init {
        loginRepo = DashboardRepository()
        isBusy = MutableLiveData()
        isWin = MutableLiveData()
        isError = MutableLiveData()
        luckyNumber = 0
        rolledNumber = 0
        message = "Try your luck... roll the dice"
        atmpt = 0

        Log.i("MV", "ViewModel init...")
    }

    fun getLuckyNumber(){
        isBusy.value = true
        ++atmpt
        message = "Rolling..."

        val payload = HashMap<String, String>()
        payload[ATMT] = atmpt.toString()

        val call1 = retrofitHelper.getLuckyNumner(payload)
        call1.enqueue(object : Callback<Roll> {
            override fun onResponse(call: Call<Roll>, response: Response<Roll>) {

                if (response.isSuccessful) {
                    onLuckyNumberRetrieved()
                } else {

                }

                isBusy.value = false
            }

            override fun onFailure(call: Call<Roll>, t: Throwable) {
                isBusy.value = false
                isError.value = true
            }
        })
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
       // luckyNumber = loginRepo.getLuckyNumber()?.luckyNumber
    }
}