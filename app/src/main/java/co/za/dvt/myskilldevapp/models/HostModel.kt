package co.za.dvt.myskilldevapp.models

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class HostModel (
    @SerializedName("id") override var id: Int = 0,
    @SerializedName("name") override var name: String? = null,

    @SerializedName("hostId") var hostId:String? = null
) : UserModel()