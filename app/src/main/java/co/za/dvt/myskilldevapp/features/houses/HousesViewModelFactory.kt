package co.za.dvt.myskilldevapp.features.houses

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HousesViewModelFactory(private val housesRepository: HousesRepository, private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HousesViewModel::class.java)){
            return HousesViewModel(housesRepository, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}