package co.za.dvt.myskilldevapp.features.spells

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class SpellsViewModelFactory(private val spellsRepository: SpellsRepository, private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SpellsViewModel::class.java)){
            return SpellsViewModel(spellsRepository, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}