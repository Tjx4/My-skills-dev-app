package co.za.dvt.myskilldevapp.models

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class MemberModel (
    @SerializedName("id") override var id: Int = 0,
    @SerializedName("name") override var name: String? = null,

    @SerializedName("memberId") var memberId:String? = null
) : UserModel()