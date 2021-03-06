package co.za.dvt.myskilldevapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class RoundModel (
    @SerializedName("user") var user: String? = null,
    @SerializedName("luckyNumber") var luckyNumber: Int = 0
): Parcelable