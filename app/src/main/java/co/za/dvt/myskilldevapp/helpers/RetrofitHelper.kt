package co.za.dvt.myskilldevapp.helpers

import co.za.dvt.myskilldevapp.constants.GET_ALL_MEMBERS
import co.za.dvt.myskilldevapp.constants.LOGIN_MEMBER
import co.za.dvt.myskilldevapp.constants.REGISTER_MEMBER
import co.za.dvt.myskilldevapp.models.LoginModel
import co.za.dvt.myskilldevapp.models.RegistrationModel
import co.za.dvt.myskilldevapp.models.UserModel
import retrofit2.Call
import retrofit2.http.*

interface RetrofitHelper {
    @FormUrlEncoded
    @POST(LOGIN_MEMBER)
    fun loginMember(@FieldMap params: Map<String, String>): Call<LoginModel>?

    @FormUrlEncoded
    @POST(REGISTER_MEMBER)
    suspend fun registerMember(@FieldMap params: Map<String, String>): RegistrationModel?

    @GET(GET_ALL_MEMBERS)
    suspend fun getAllUsers(@Header("authorization") token: String): List<UserModel>?

}