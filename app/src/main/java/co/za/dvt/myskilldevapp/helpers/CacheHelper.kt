package co.za.dvt.myskilldevapp.helpers

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class CacheHelper(context: Context) {
    private val sharedPreferences: SharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)

    var apiKey: String
        get() {
            return sharedPreferences.getString(API_KEY, "") ?: ""
        }
        set(apiKey){
            val editor = sharedPreferences.edit()
            editor.putString(API_KEY, apiKey)
            editor.commit()
        }

    companion object {
        private val API_KEY = "api_key"
    }
}