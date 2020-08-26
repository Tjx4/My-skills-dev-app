package co.za.dvt.myskilldevapp.helpers

import co.za.dvt.myskilldevapp.constants.GET_ALL_MEMBERS
import co.za.dvt.myskilldevapp.constants.LOGIN_MEMBER
import co.za.dvt.myskilldevapp.models.LoginModel
import co.za.dvt.myskilldevapp.models.UserModel
import retrofit2.http.*

interface RetrofitHelper {
    //@POST(LOGIN_MEMBER)
    //suspend fun loginMember(@QueryMap params: Map<String, String>): LoginModel

    @GET(LOGIN_MEMBER)
    suspend fun loginMember(@Path(value = "username", encoded = true)username: String?, @Path(value = "password", encoded = true)password: String?): LoginModel?

    @GET(GET_ALL_MEMBERS)
    suspend fun getAllUsers(@Header("authorization") token: String): List<UserModel>?

}