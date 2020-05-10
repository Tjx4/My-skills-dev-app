package co.za.dvt.myskilldevapp.helpers

import co.za.dvt.myskilldevapp.constants.*
import co.za.dvt.myskilldevapp.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RetrofitHelper {
    @GET(GET_LUCKY_NUMBER)
    suspend fun getLuckyNumner(@Header("authorization") token: String, @QueryMap params: Map<String, String>): RoundModel?

    @GET(GET_AVAILABLE_CARS)
    suspend fun getAvailableCars(): List<CarModel>?







    @GET(GET_HOUSES)
    suspend fun getHouses(@Query("api_key") api_key: String?): List<House>?

    @GET(GET_STUDENTS)
    suspend fun getStudents(@Query("api_key") api_key: String?): List<Student>?

    @GET(GET_SPELLS)
    suspend fun getSpells(@Query("api_key") api_key: String?): List<Spell>?


}