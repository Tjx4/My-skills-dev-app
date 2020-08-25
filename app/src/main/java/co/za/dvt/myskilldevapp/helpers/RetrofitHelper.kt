package co.za.dvt.myskilldevapp.helpers

import co.za.dvt.myskilldevapp.constants.GET_ALL_MEMBERS
import co.za.dvt.myskilldevapp.constants.LOGIN_MEMBER
import co.za.dvt.myskilldevapp.models.LoginModel
import co.za.dvt.myskilldevapp.models.UserModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface RetrofitHelper {
    @POST(LOGIN_MEMBER)
    suspend fun getLuckyNumner(@QueryMap params: Map<String, String>): LoginModel?

    @GET(GET_ALL_MEMBERS)
    suspend fun getAllUsers(@Header("authorization") token: String): List<UserModel>?
}