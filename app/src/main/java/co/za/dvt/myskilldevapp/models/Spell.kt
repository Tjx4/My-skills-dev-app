package co.za.dvt.myskilldevapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Spell (
    @SerializedName("_id") var id: String? = null,
    @SerializedName("spell") var spell: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("effect") var effect: String? = null
): Parcelable
