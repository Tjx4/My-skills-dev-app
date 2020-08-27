package co.za.dvt.myskilldevapp.models

import com.google.gson.annotations.SerializedName

data class LoginModel (
    @SerializedName("success")  var success: Boolean = false,
    @SerializedName("message")  var message: String? = null,

    @SerializedName("token") var token: String?,
    var user: UserModel?
)