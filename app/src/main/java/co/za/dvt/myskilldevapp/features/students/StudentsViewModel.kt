package co.za.dvt.myskilldevapp.features.students

import android.app.Application
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import kotlinx.coroutines.launch

class StudentsViewModel(val studentsRepository: StudentsRepository, application: Application) : BaseVieModel(application)  {

    fun getAndShowStudents(){
        ioScope.launch {
            var apiKey = cacheHelper.apiKey
            val students = studentsRepository.getStudents(apiKey)

            uiScope.launch {

                if(!students.isNullOrEmpty()) {
                    // students found
                }
                else{
                    // students not found
                }
            }
        }
    }
}