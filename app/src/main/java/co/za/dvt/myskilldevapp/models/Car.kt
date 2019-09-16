package co.za.dvt.myskilldevapp.models

import com.google.gson.annotations.SerializedName

class Car {
    @SerializedName("brand")
    var brand:String? = null
    @SerializedName("model")
    var model:String? = null
    @SerializedName("year")
    var year:String? = null
    @SerializedName("color")
    var color:String? = null
    @SerializedName("pic")
    var pic:String? = null
}