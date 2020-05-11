package co.za.dvt.myskilldevapp.features.characters

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.models.Character
import kotlinx.coroutines.launch

class CharatersViewModel(val charatersRepository: CharatersRepository, application: Application) : BaseVieModel(application)  {

    private var _isBusy: MutableLiveData<Boolean>  = MutableLiveData()
    val isBusy: LiveData<Boolean>
        get() = _isBusy

    private var _characters: MutableLiveData<List<Character?>?> = MutableLiveData()
    val characters: LiveData<List<Character?>?>
        get() = _characters

    fun getAndShowStudents(){
        _isBusy.value = true

        ioScope.launch {
            val students = charatersRepository.getStudents(cacheHelper.apiKey)

            uiScope.launch {

                if(!students.isNullOrEmpty()) {
                    _characters.value = students
                }
                else{
                    // students not found
                }

                _isBusy.value = false
            }
        }
    }
}