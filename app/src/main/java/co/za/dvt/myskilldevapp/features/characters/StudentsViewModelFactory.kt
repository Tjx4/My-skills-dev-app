package co.za.dvt.myskilldevapp.features.characters

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StudentsViewModelFactory(private val studentsRepository: StudentsRepository, private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StudentsViewModel::class.java)){
            return StudentsViewModel(studentsRepository, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}