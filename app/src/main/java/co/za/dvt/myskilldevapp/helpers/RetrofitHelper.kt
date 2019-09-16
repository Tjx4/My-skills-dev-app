package co.za.dvt.myskilldevapp.helpers

import co.za.dvt.myskilldevapp.constants.GET_AVAILABLE_CARS
import co.za.dvt.myskilldevapp.constants.GET_LUCKY_NUMBER
import co.za.dvt.myskilldevapp.models.Car
import co.za.dvt.myskilldevapp.models.Roll
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RetrofitHelper {
    @GET(GET_LUCKY_NUMBER)
    fun getLuckyNumner(@QueryMap params: Map<String, String>): Call<Roll>

    @GET(GET_AVAILABLE_CARS)
    fun getAvailableCars(@QueryMap params: Map<String, String>): Call<List<Car>>
}