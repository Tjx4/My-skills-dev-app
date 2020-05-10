package co.za.dvt.myskilldevapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class House (
    @SerializedName("_id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("mascot") var mascot: String? = null,
    @SerializedName("headOfHouse") var headOfHouse: String? = null,
    @SerializedName("houseGhost") var houseGhost: String? = null,
    @SerializedName("founder") var founder: String? = null,
    @SerializedName("__v") var v: Int? = 0,
    @SerializedName("school") var school: String? = null,
    @SerializedName("members") var members:  List<String?>?  = null,
    @SerializedName("values") var values:  List<String?>?  = null,
    @SerializedName("colors") var colors: List<String?>? = null
): Parcelable


