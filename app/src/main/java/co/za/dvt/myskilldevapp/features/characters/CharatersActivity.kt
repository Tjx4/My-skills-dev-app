package co.za.dvt.myskilldevapp.features.characters

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.CharactersAdapter
import co.za.dvt.myskilldevapp.constants.CHARACTER
import co.za.dvt.myskilldevapp.constants.HOUSE
import co.za.dvt.myskilldevapp.constants.PAYLOAD_KEY
import co.za.dvt.myskilldevapp.databinding.ActivityStudentsBinding
import co.za.dvt.myskilldevapp.extensions.SLIDE_IN_ACTIVITY
import co.za.dvt.myskilldevapp.extensions.goToActivityWithPayload
import co.za.dvt.myskilldevapp.features.activities.BaseChildActivity
import co.za.dvt.myskilldevapp.helpers.hideCurrentLoadingDialog
import co.za.dvt.myskilldevapp.helpers.showLoadingDialog
import co.za.dvt.myskilldevapp.models.Character
import co.za.dvt.myskilldevapp.models.House
import kotlinx.android.synthetic.main.activity_students.*

class CharatersActivity : BaseChildActivity(), CharactersAdapter.CharacterClickListener {
    private lateinit var binding: ActivityStudentsBinding
    private lateinit var charactersViewModel: CharatersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.characters)

        var studentsRepository = CharatersRepository()
        var application = requireNotNull(this).application
        var viewModelFactory = CharatersViewModelFactory(studentsRepository, application)

        charactersViewModel = ViewModelProviders.of(this, viewModelFactory).get(CharatersViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_students)
        binding.studentsViewModel = charactersViewModel
        binding.lifecycleOwner = this

        charactersViewModel.isBusy.observe(this, Observer { isBusy(it) })
        charactersViewModel.characters.observe(this, Observer { onCharactersSet(it) })

        charactersViewModel.getAndShowStudents()
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
        val selectedCharacter = charactersViewModel.characters?.value?.get(position)

        var payload = Bundle()
        payload.putParcelable(CHARACTER, selectedCharacter)
        goToActivityWithPayload(CharacterActivity::class.java, payload, SLIDE_IN_ACTIVITY)
    }

}
