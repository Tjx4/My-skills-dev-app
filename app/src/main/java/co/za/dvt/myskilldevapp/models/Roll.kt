package co.za.dvt.myskilldevapp.models

import com.google.gson.annotations.SerializedName

class Roll {
    @SerializedName("attempt")
    var attempt:Int = 0
    @SerializedName("currentLuckyNumber")
    var luckyNumber:Int = 0
}