package co.za.dvt.myskilldevapp.models

import com.google.gson.annotations.SerializedName

class LuckyNumber {
    @SerializedName("attempt")
    var attempt:Int = 0
    @SerializedName("luckyNumberModel")
    var luckyNumber:Int = 0
}