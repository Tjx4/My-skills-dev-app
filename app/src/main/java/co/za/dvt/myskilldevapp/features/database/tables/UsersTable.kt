package co.za.dvt.myskilldevapp.features.database.tables

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users")
data class UsersTable (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long = 0L,

    @ColumnInfo(name = "name")
    var name:String? = null,

    @ColumnInfo(name = "surname")
    var surname:String? = null,

    @ColumnInfo(name = "userType")
    var userType:Int = 0
): Parcelable