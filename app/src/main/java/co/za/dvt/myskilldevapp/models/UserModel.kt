package co.za.dvt.myskilldevapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class UserModel (
    @SerializedName("id") open var id: Int = 0,
    @SerializedName("name") open var name: String? = null,
    @SerializedName("surname") open var surname: String? = null,
    @SerializedName("age") open var age: Int = 0
): Parcelable