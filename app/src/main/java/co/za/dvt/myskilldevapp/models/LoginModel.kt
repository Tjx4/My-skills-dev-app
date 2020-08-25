package co.za.dvt.myskilldevapp.models

import com.google.gson.annotations.SerializedName

class LoginModel(@SerializedName("success") var success: Boolean, @SerializedName("token") var token: String, user: UserModel)