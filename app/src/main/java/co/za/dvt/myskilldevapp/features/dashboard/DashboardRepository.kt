package co.za.dvt.myskilldevapp.features.dashboard

import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.constants.ATMT
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import co.za.dvt.myskilldevapp.models.Car
import co.za.dvt.myskilldevapp.models.RoundModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

open class DashboardRepository : BaseRepositories() {

    val roundModel: MutableLiveData<RoundModel?> = MutableLiveData()
    val availableCars: MutableLiveData<List<Car>?> = MutableLiveData()
    var atmpt: Int = 0

    init {
        roundModel.value = RoundModel()
    }

    suspend fun fetchLuckyNumber(): RoundModel{
        ++atmpt

        val payload = HashMap<String, String>()
        payload[ATMT] = atmpt.toString()

        val call1 = retrofitHelper?.getLuckyNumner(payload)

        call1?.enqueue(object : Callback<RoundModel> {
            override fun onResponse(call: Call<RoundModel>, response: Response<RoundModel>) {

                if (response.isSuccessful) {
                    roundModel.value = response.body()
                    atmpt = 0
                } else {
                    roundModel.value = null
                }
            }

            override fun onFailure(call: Call<RoundModel>, t: Throwable) {
                roundModel.value = null
            }
        })
    }

    suspend fun fetchAvailableCars(): List<Car> {
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