package co.za.dvt.myskilldevapp.models

import com.google.gson.annotations.SerializedName

data class OtpModel (
    @SerializedName("success")  var success: Boolean = false,
    @SerializedName("message")  var message: String? = null,
    @SerializedName("otp")  var otp: String? = null
)