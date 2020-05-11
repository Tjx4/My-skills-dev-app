package co.za.dvt.myskilldevapp.features.houses

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.CharactersAdapter
import co.za.dvt.myskilldevapp.constants.HOUSE
import co.za.dvt.myskilldevapp.constants.PAYLOAD_KEY
import co.za.dvt.myskilldevapp.features.activities.BaseChildActivity
import co.za.dvt.myskilldevapp.models.House
import co.za.dvt.myskilldevapp.models.Character
import kotlinx.android.synthetic.main.activity_house.*

class HouseActivity : BaseChildActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house)

        val house = intent.getBundleExtra(PAYLOAD_KEY)?.getParcelable<House>(HOUSE)
        supportActionBar?.title = house?.name
        txtHouseMascot.text = house?.mascot
        //txtHeadOfHouse.text = house?.headOfHouse
        txtHouseFounder.text = house?.founder
        txtHouseGhost.text = house?.houseGhost
    }

    fun showMembers(members: List<Character?>){
        rvMembers?.layoutManager = LinearLayoutManager(this)
        val charactersAdapter = CharactersAdapter(this, R.layout.character_layout, members)
        //charactersAdapter.setClickListener(this)
        rvMembers?.adapter = charactersAdapter
    }
}
