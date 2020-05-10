package co.za.dvt.myskilldevapp.features.houses

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.models.House
import kotlinx.coroutines.launch

class HousesViewModel(val housesRepository: HousesRepository, application: Application) : BaseVieModel(application)  {

    private var _isBusy: MutableLiveData<Boolean>  = MutableLiveData()
    val isBusy: LiveData<Boolean>
        get() = _isBusy

    private var _houses: MutableLiveData<List<House?>?> = MutableLiveData()
    val houses: LiveData<List<House?>?>
        get() = _houses

    fun getAndShowHouses(){
        _isBusy.value = true

        ioScope.launch {
            val houses = housesRepository.getHouses(cacheHelper.apiKey)

            uiScope.launch {

                if(!houses.isNullOrEmpty()) {
                    _houses.value = houses
                }
                else{
                    // houses not found
                }

                _isBusy.value = false
            }
        }
    }

}