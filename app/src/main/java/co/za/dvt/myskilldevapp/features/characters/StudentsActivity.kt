package co.za.dvt.myskilldevapp.features.characters

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.CharactersAdapter
import co.za.dvt.myskilldevapp.databinding.ActivityStudentsBinding
import co.za.dvt.myskilldevapp.features.activities.BaseChildActivity
import co.za.dvt.myskilldevapp.helpers.hideCurrentLoadingDialog
import co.za.dvt.myskilldevapp.helpers.showLoadingDialog
import co.za.dvt.myskilldevapp.models.Character
import kotlinx.android.synthetic.main.activity_houses.*
import kotlinx.android.synthetic.main.activity_students.*

class StudentsActivity : BaseChildActivity(), CharactersAdapter.CharacterClickListener {
    private lateinit var binding: ActivityStudentsBinding
    private lateinit var studentsViewModel: StudentsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.characters)

        var studentsRepository = StudentsRepository()
        var application = requireNotNull(this).application
        var viewModelFactory = StudentsViewModelFactory(studentsRepository, application)

        studentsViewModel = ViewModelProviders.of(this, viewModelFactory).get(StudentsViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_students)
        binding.studentsViewModel = studentsViewModel
        binding.lifecycleOwner = this

        studentsViewModel.isBusy.observe(this, Observer { isBusy(it) })
        studentsViewModel.students.observe(this, Observer { onCharactersSet(it) })

        studentsViewModel.getAndShowStudents()
    }

    private fun isBusy(isBusy: Boolean){
        if(isBusy)
            showLoadingDialog("please wait...", this)
        else
            hideCurrentLoadingDialog(this)
    }

    private fun onCharactersSet(characters: List<Character?>?){
        rvCharacters?.layoutManager = LinearLayoutManager(this)
        val housesAdapter = CharactersAdapter(this, R.layout.character_layout ,characters)
        housesAdapter.setClickListener(this)
        rvCharacters?.adapter = housesAdapter
    }

    override fun onCharacterClick(view: View, position: Int) {

    }

}
