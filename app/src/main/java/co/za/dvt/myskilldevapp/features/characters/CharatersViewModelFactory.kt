package co.za.dvt.myskilldevapp.features.characters

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CharatersViewModelFactory(private val charatersRepository: CharatersRepository, private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CharatersViewModel::class.java)){
            return CharatersViewModel(charatersRepository, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}