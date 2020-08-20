package co.za.dvt.myskilldevapp.helpers

import co.za.dvt.myskilldevapp.constants.GET_AVAILABLE_CARS
import co.za.dvt.myskilldevapp.constants.GET_LUCKY_NUMBER
import co.za.dvt.myskilldevapp.models.HostModel
import co.za.dvt.myskilldevapp.models.UserModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface RetrofitHelper {
    @GET(GET_LUCKY_NUMBER)
    suspend fun getLuckyNumner(@Header("authorization") token: String, @QueryMap params: Map<String, String>): UserModel?

    @GET(GET_AVAILABLE_CARS)
    suspend fun getAvailableCars(): List<HostModel>?
}