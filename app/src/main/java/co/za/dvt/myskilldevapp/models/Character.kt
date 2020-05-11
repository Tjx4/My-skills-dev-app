package co.za.dvt.myskilldevapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    @SerializedName("_id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("role") var role: String? = null,
    @SerializedName("house") var house: String? = null,
    @SerializedName("school") var school: String? = null,
    @SerializedName("__v") var v: Int? = 0,
    @SerializedName("ministryOfMagic") var ministryOfMagic: Boolean = false,
    @SerializedName("orderOfThePhoenix") var orderOfThePhoenix: Boolean = false,
    @SerializedName("dumbledoresArmy") var dumbledoresArmy: Boolean = false,
    @SerializedName("deathEater") var deathEater: Boolean = false,
    @SerializedName("bloodStatus") var bloodStatus: String? = null,
    @SerializedName("species") var species: String? = null
): Parcelable