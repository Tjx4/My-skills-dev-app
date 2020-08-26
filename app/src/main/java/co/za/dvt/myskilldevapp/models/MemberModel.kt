package co.za.dvt.myskilldevapp.models

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class MemberModel (
    @SerializedName("id") override var id: Int = 0,
    @SerializedName("username") override var username: String? = null,
    @SerializedName("name") override var name: String? = null,
    @SerializedName("surname") override var surname: String? = null,
    @SerializedName("age") override var age: Int = 0,

    @SerializedName("memberBalance") var memberBalance:String? = null
) : UserModel()