package co.za.dvt.myskilldevapp.features.dashboard

import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.constants.ATMT
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import co.za.dvt.myskilldevapp.models.Car
import co.za.dvt.myskilldevapp.models.Roll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class DashboardRepository : BaseRepositories() {

    //Todo: fix logic
    val dashboardModel: MutableLiveData<DashboardModel>
    var atmpt: Int

    init {
        dashboardModel = MutableLiveData()
        atmpt = 0
    }

    fun fetchLuckyNumberFromRepo() {
        ++atmpt

        val payload = HashMap<String, String>()
        payload[ATMT] = atmpt.toString()

        val call1 = retrofitHelper.getLuckyNumner(payload)
        call1.enqueue(object : Callback<Roll> {
            override fun onResponse(call: Call<Roll>, response: Response<Roll>) {

                if (response.isSuccessful) {
                    dashboardModel.value?.roll = response.body()
                    atmpt = 0
                } else {

                }

               // isBusy.value = false
            }

            override fun onFailure(call: Call<Roll>, t: Throwable) {
               // isBusy.value = false
               // isError.value = true
            }
        })
    }

    fun fetchAvailableCarsFromRepo() {
        val call1 = retrofitHelper.getAvailableCars()
        call1.enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {

                if (response.isSuccessful) {
                    dashboardModel.value?.availableCars = response.body()
                } else {

                }

                // isBusy.value = false
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                // isBusy.value = false
                // isError.value = true
            }
        })
    }

}

