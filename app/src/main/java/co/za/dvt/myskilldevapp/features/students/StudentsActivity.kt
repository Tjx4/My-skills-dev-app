package co.za.dvt.myskilldevapp.features.students

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.databinding.ActivityStudentsBinding

class StudentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentsBinding
    private lateinit var studentsViewModel: StudentsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var studentsRepository = StudentsRepository()
        var application = requireNotNull(this).application
        var viewModelFactory = StudentsViewModelFactory(studentsRepository, application)

        studentsViewModel = ViewModelProviders.of(this, viewModelFactory).get(StudentsViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_students)
        binding.studentsViewModel = studentsViewModel
        binding.lifecycleOwner = this
    }
}
