package co.za.dvt.myskilldevapp.features.characters

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.models.Character
import kotlinx.coroutines.launch

class StudentsViewModel(val studentsRepository: StudentsRepository, application: Application) : BaseVieModel(application)  {

    private var _isBusy: MutableLiveData<Boolean>  = MutableLiveData()
    val isBusy: LiveData<Boolean>
        get() = _isBusy

    private var _students: MutableLiveData<List<Character?>?> = MutableLiveData()
    val students: LiveData<List<Character?>?>
        get() = _students

    fun getAndShowStudents(){
        _isBusy.value = true

        ioScope.launch {
            val students = studentsRepository.getStudents(cacheHelper.apiKey)

            uiScope.launch {

                if(!students.isNullOrEmpty()) {
                    _students.value = students
                }
                else{
                    // students not found
                }

                _isBusy.value = false
            }
        }
    }
}