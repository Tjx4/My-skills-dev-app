package co.za.dvt.myskilldevapp.models

import com.google.gson.annotations.SerializedName

open class BaseApiModel(
    @SerializedName("success") open var success: Boolean = false,
    @SerializedName("message") open var message: String? = null
)
