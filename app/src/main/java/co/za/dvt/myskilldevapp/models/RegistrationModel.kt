package co.za.dvt.myskilldevapp.models

import com.google.gson.annotations.SerializedName

data class RegistrationModel(
    @SerializedName("success")  var success: Boolean = false,
    @SerializedName("message")  var message: String? = null
)