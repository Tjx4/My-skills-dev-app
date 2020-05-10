package co.za.dvt.myskilldevapp.features.houses

import android.app.Application
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import kotlinx.coroutines.launch

class HousesViewModel(val housesRepository: HousesRepository, application: Application) : BaseVieModel(application)  {

    fun getAndShowHouses(){
        ioScope.launch {
            var apiKey = cacheHelper.apiKey
            val houses = housesRepository.getHouses(apiKey)

            uiScope.launch {

                if(!houses.isNullOrEmpty()) {
                    // houses found
                }
                else{
                    // houses not found
                }
            }
        }
    }

}