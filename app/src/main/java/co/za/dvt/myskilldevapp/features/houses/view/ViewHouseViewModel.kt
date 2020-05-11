package co.za.dvt.myskilldevapp.features.houses.view

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.features.characters.CharatersRepository
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.models.Character
import co.za.dvt.myskilldevapp.models.House
import kotlinx.coroutines.launch

class ViewHouseViewModel(val house: House, val charatersRepository: CharatersRepository, application: Application) : BaseVieModel(application)  {

    private var _isBusy: MutableLiveData<Boolean>  = MutableLiveData()
    val isBusy: LiveData<Boolean>
        get() = _isBusy

    private var _members: MutableLiveData<List<Character?>?> = MutableLiveData()
    val members: LiveData<List<Character?>?>
        get() = _members

    fun getAndShowHouses(){
        _isBusy.value = true

        ioScope.launch {
            val members = charatersRepository.getCharactersInhouse(cacheHelper.apiKey, house.name ?: "")

            uiScope.launch {

                if(!members.isNullOrEmpty()) {
                    _members.value = members
                }
                else{
                    // houses not found
                }

                _isBusy.value = false
            }
        }
    }
}