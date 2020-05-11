package co.za.dvt.myskilldevapp.features.spells

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.constants.HOUSE
import co.za.dvt.myskilldevapp.constants.PAYLOAD_KEY
import co.za.dvt.myskilldevapp.constants.SPELL
import co.za.dvt.myskilldevapp.features.activities.BaseChildActivity
import co.za.dvt.myskilldevapp.models.House
import co.za.dvt.myskilldevapp.models.Spell
import kotlinx.android.synthetic.main.activity_house.*
import kotlinx.android.synthetic.main.activity_spell.*

class SpellActivity : BaseChildActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spell)

        val spell = intent.getBundleExtra(PAYLOAD_KEY)?.getParcelable<Spell>(SPELL)
        supportActionBar?.title = spell?.spell
        tvSpellEffect.text = "Effect: ${spell?.effect}"
        tvSpellType.text = "Type: ${spell?.type}"
    }
}
