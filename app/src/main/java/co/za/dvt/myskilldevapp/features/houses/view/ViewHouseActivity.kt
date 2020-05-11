package co.za.dvt.myskilldevapp.features.houses.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.CharactersAdapter
import co.za.dvt.myskilldevapp.constants.HOUSE
import co.za.dvt.myskilldevapp.constants.PAYLOAD_KEY
import co.za.dvt.myskilldevapp.databinding.ActivityHousesBinding
import co.za.dvt.myskilldevapp.features.activities.BaseChildActivity
import co.za.dvt.myskilldevapp.features.characters.CharatersRepository
import co.za.dvt.myskilldevapp.models.House
import co.za.dvt.myskilldevapp.models.Character
import kotlinx.android.synthetic.main.activity_house.*

class ViewHouseActivity : BaseChildActivity() {
    private lateinit var binding: ActivityViewHouseBinding
    private lateinit var viewHouseViewModel: ViewHouseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val house = intent.getBundleExtra(PAYLOAD_KEY)?.getParcelable(HOUSE) ?: House()  //Todo: Fix
        supportActionBar?.title = house?.name
        txtHouseMascot.text = house?.mascot
        //txtHeadOfHouse.text = house?.headOfHouse
        txtHouseFounder.text = house?.founder
        txtHouseGhost.text = house?.houseGhost
        
        var charatersRepository = CharatersRepository()
        var application = requireNotNull(this).application
        var viewModelFactory = ViewHouseModelFactory(house, charatersRepository, application)

        viewHouseViewModel = ViewModelProviders.of(this, viewModelFactory).get(ViewHouseViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_house)
        binding.viewHouseViewModel = viewHouseViewModel
        binding.lifecycleOwner = this

        viewHouseViewModel.members.observe(this, Observer { showMembers(it) })
    }

    fun showMembers(members: List<Character?>?){
        rvMembers?.layoutManager = LinearLayoutManager(this)
        val charactersAdapter = CharactersAdapter(this, R.layout.character_layout, members)
        //charactersAdapter.setClickListener(this)
        rvMembers?.adapter = charactersAdapter
    }
}
