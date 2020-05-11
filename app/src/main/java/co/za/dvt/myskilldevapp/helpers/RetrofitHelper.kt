package co.za.dvt.myskilldevapp.helpers

import co.za.dvt.myskilldevapp.constants.*
import co.za.dvt.myskilldevapp.models.*
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
    suspend fun getHouses(@Query("key") api_key: String?): List<House>?

    @GET(GET_STUDENTS)
    suspend fun getCharacters(@Query("key") api_key: String?): List<Character>?

    @GET(GET_STUDENTS)
    suspend fun getCharactersInHouse(@Query("key") api_key: String?, @Query("house") house: String?): List<Character>?

    @GET(GET_SPELLS)
    suspend fun getSpells(@Query("key") api_key: String?): List<Spell>?


}