package co.za.dvt.myskilldevapp.features.dashboard

import co.za.dvt.myskilldevapp.constants.ATMT
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import co.za.dvt.myskilldevapp.models.Car
import co.za.dvt.myskilldevapp.models.RoundModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

open class DashboardRepository : BaseRepositories() {

    suspend fun fetchLuckyNumber(): RoundModel?{
        var roundModel: RoundModel? = null

        val payload = HashMap<String, String>()
        payload[ATMT] = "0"

        val call1 = retrofitHelper?.getLuckyNumner(payload)

        call1?.enqueue(object : Callback<RoundModel> {
            override fun onResponse(call: Call<RoundModel>, response: Response<RoundModel>) {

                if (response.isSuccessful) {
                    roundModel = response.body()
                } else {
                    roundModel = null
                }
            }

            override fun onFailure(call: Call<RoundModel>, t: Throwable) {
                roundModel = null
            }
        })

        return roundModel
    }

    suspend fun fetchAvailableCars(): List<Car>? {
        var availableCars: List<Car>? = ArrayList()
        val call1 = retrofitHelper.getAvailableCars()
        call1.enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {

                if (response.isSuccessful) {
                    availableCars = response.body()
                }
                else{
                    availableCars = null
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                availableCars = null
            }
        })

        return availableCars
    }
}