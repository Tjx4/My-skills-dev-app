package co.za.dvt.myskilldevapp.helpers

import co.za.dvt.myskilldevapp.constants.*
import co.za.dvt.myskilldevapp.models.*
import retrofit2.Call
import retrofit2.http.*

interface RetrofitHelper {
    @FormUrlEncoded
    @POST(LOGIN_MEMBER)
    fun loginMember(@FieldMap params: Map<String, String>): Call<LoginModel>?

    @FormUrlEncoded
    @POST(REGISTER_MEMBER)
    suspend fun registerMember(@FieldMap params: Map<String, String>): RegistrationModel?

    @FormUrlEncoded
    @POST(RESET_PASSWORD)
    suspend fun resetPassword(@FieldMap params: Map<String, String>): ResetModel?

    @GET(GET_OTP)
    suspend fun getOTP(@Header("email") email: String, @Header("mobile") mobile: String): OtpModel?

    @GET(GET_ALL_MEMBERS)
    suspend fun getAllUsers(@Header("authorization") token: String): List<UserModel>?

}