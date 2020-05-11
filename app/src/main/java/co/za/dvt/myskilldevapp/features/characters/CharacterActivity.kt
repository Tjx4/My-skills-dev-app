package co.za.dvt.myskilldevapp.features.characters

import android.os.Bundle
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.constants.CHARACTER
import co.za.dvt.myskilldevapp.constants.PAYLOAD_KEY
import co.za.dvt.myskilldevapp.features.activities.BaseChildActivity
import co.za.dvt.myskilldevapp.models.Character
import kotlinx.android.synthetic.main.activity_charater.*

class CharacterActivity : BaseChildActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charater)

        val character = intent.getBundleExtra(PAYLOAD_KEY)?.getParcelable<Character>(CHARACTER)
        supportActionBar?.title = character?.name
        tvCharacterHouse.text = "House: ${character?.house}"
        tvCharacterRole.text = "Role: ${character?.role}"
    }
}
