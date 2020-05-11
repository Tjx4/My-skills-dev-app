package co.za.dvt.myskilldevapp.features.houses.view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.za.dvt.myskilldevapp.features.characters.CharatersRepository
import co.za.dvt.myskilldevapp.models.House

class ViewHouseModelFactory(private val house: House, private val charatersRepository: CharatersRepository, private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ViewHouseViewModel::class.java)){
            return ViewHouseViewModel(house, charatersRepository, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}