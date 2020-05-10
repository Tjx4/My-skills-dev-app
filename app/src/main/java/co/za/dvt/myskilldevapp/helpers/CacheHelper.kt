package co.za.dvt.myskilldevapp.helpers

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder


class CacheHelper(private val activity: AppCompatActivity) {
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)

    var apiKey: String?
        get() {
            // $2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y
            return sharedPreferences.getString(API_KEY, "")
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