package co.za.dvt.myskilldevapp.features.houses.view

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.CharactersAdapter
import co.za.dvt.myskilldevapp.constants.CHARACTER
import co.za.dvt.myskilldevapp.constants.HOUSE
import co.za.dvt.myskilldevapp.constants.PAYLOAD_KEY
import co.za.dvt.myskilldevapp.databinding.ActivityHouseBinding
import co.za.dvt.myskilldevapp.extensions.SLIDE_IN_ACTIVITY
import co.za.dvt.myskilldevapp.extensions.blinkView
import co.za.dvt.myskilldevapp.extensions.goToActivityWithPayload
import co.za.dvt.myskilldevapp.features.activities.BaseChildActivity
import co.za.dvt.myskilldevapp.features.characters.CharacterActivity
import co.za.dvt.myskilldevapp.features.characters.CharatersRepository
import co.za.dvt.myskilldevapp.models.House
import co.za.dvt.myskilldevapp.models.Character
import kotlinx.android.synthetic.main.activity_house.*

class ViewHouseActivity : BaseChildActivity(), CharactersAdapter.CharacterClickListener {
    private lateinit var binding: ActivityHouseBinding
    private lateinit var viewHouseViewModel: ViewHouseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val house = intent.getBundleExtra(PAYLOAD_KEY)?.getParcelable(HOUSE) ?: House()  //Todo: Fix

        var charatersRepository = CharatersRepository()
        var application = requireNotNull(this).application
        var viewModelFactory = ViewHouseModelFactory(house, charatersRepository, application)

        viewHouseViewModel = ViewModelProviders.of(this, viewModelFactory).get(ViewHouseViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_house)
        binding.viewHouseViewModel = viewHouseViewModel
        binding.lifecycleOwner = this

        supportActionBar?.title = house?.name
        txtHouseMascot.text = house?.mascot
        //txtHeadOfHouse.text = house?.headOfHouse
        txtHouseFounder.text = house?.founder
        txtHouseGhost.text = house?.houseGhost

        viewHouseViewModel.isBusy.observe(this, Observer { isBusy(it) })
        viewHouseViewModel.members.observe(this, Observer { onMembersSet(it) })

        viewHouseViewModel.getAndShowHouses()
    }

    private fun isBusy(isBusy: Boolean){
        llMemberLoader.isVisible = isBusy
    }

    fun onMembersSet(members: List<Character?>?){
        rvMembers?.layoutManager = LinearLayoutManager(this)
        val charactersAdapter = CharactersAdapter(this, R.layout.small_character_layout, members)
        charactersAdapter.setClickListener(this)
        rvMembers?.adapter = charactersAdapter
    }

    override fun onCharacterClick(view: View, position: Int) {
        (view as FrameLayout).getChildAt(0).blinkView(0.5f, 1.0f, 400, 2, Animation.ABSOLUTE, 0, {
            val selectedCharacter = viewHouseViewModel.members?.value?.get(position)

            var payload = Bundle()
            payload.putParcelable(CHARACTER, selectedCharacter)
            goToActivityWithPayload(CharacterActivity::class.java, payload, SLIDE_IN_ACTIVITY)
        }, {})
    }
}
