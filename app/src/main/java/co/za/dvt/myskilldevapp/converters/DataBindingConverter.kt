package co.za.dvt.myskilldevapp.converters

import android.text.TextUtils
import androidx.databinding.InverseMethod

class DataBindingConverter {
    fun convertStringToInteger(value: String): Int? {
        if (TextUtils.isEmpty(value) || !TextUtils.isDigitsOnly(value)) {
            return null
        }
        return value.toIntOrNull()
    }

    fun convertIntegerToString(value: Int?): String {
        return value?.toString() ?: ""
    }
}