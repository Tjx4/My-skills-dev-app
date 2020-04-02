package co.za.dvt.myskilldevapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Car (
    @SerializedName("brand") var brand:String? = null,
    @SerializedName("model") var model:String? = null,
    @SerializedName("year") var year:String? = null,
    @SerializedName("color") var color:String? = null,
    @SerializedName("pic") var pic:String? = null
) : Parcelable