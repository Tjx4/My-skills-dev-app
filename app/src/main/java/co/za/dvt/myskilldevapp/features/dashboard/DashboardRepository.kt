package co.za.dvt.myskilldevapp.features.dashboard

import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.constants.ATMT
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import co.za.dvt.myskilldevapp.models.Car
import co.za.dvt.myskilldevapp.models.LuckyNumber
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class DashboardRepository : BaseRepositories() {

    val luckyNumber: MutableLiveData<LuckyNumber?> = MutableLiveData()
    val availableCars: MutableLiveData<List<Car>?> = MutableLiveData()
    var atmpt: Int = 0

    init {
        luckyNumber.value = LuckyNumber()
    }

    fun fetchLuckyNumber(){
        ++atmpt

        val payload = HashMap<String, String>()
        payload[ATMT] = atmpt.toString()

        val call1 = retrofitHelper.getLuckyNumner(payload)
        call1.enqueue(object : Callback<LuckyNumber> {
            override fun onResponse(call: Call<LuckyNumber>, response: Response<LuckyNumber>) {

                if (response.isSuccessful) {
                    luckyNumber.value = response.body()
                    atmpt = 0
                } else {
                    luckyNumber.value = null
                }
            }

            override fun onFailure(call: Call<LuckyNumber>, t: Throwable) {
                luckyNumber.value = null
            }
        })
    }

    fun fetchAvailableCars() {
        val call1 = retrofitHelper.getAvailableCars()
        call1.enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {

                if (response.isSuccessful) {
                    availableCars.value = response.body()
                }
                else{
                    availableCars.value = null
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                availableCars.value = null
            }
        })
    }

}