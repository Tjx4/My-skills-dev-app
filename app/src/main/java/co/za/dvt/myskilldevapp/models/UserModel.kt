package co.za.dvt.myskilldevapp.models

import android.os.Parcelable
import co.za.dvt.myskilldevapp.enums.Gender
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class UserModel (
    @SerializedName("id") open var id: Int = 0,
    @SerializedName("username") open var username: String? = null,
    @SerializedName("name") open var name: String? = null,
    @SerializedName("surname") open var surname: String? = null,
    @SerializedName("email") open var email: String? = null,
    @SerializedName("mobile") open var mobile: String? = null,
    @SerializedName("picUrl") open var picUrl: String? = null,
    @SerializedName("age") open var age: Int = 0
   // @SerializedName("gender") open var gender: Gender? = null
): Parcelable