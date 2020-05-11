package co.za.dvt.myskilldevapp.features.spells

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.features.houses.HousesRepository
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.models.House
import co.za.dvt.myskilldevapp.models.Spell
import kotlinx.coroutines.launch

class SpellsViewModel(val spellsRepository: SpellsRepository, application: Application) : BaseVieModel(application)  {

    private var _isBusy: MutableLiveData<Boolean> = MutableLiveData()
    val isBusy: LiveData<Boolean>
        get() = _isBusy

    private var _spells: MutableLiveData<List<Spell?>?> = MutableLiveData()
    val spells: LiveData<List<Spell?>?>
        get() = _spells

    fun getAndShowSpells(){
        _isBusy.value = true

        ioScope.launch {
            val spells = spellsRepository.getSpells(cacheHelper.apiKey)

            uiScope.launch {

                if(!spells.isNullOrEmpty()) {
                    _spells.value = spells
                }
                else{
                    // houses not found
                }

                _isBusy.value = false
            }
        }
    }

}