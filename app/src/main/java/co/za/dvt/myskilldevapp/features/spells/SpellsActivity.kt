package co.za.dvt.myskilldevapp.features.spells

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.SpellsAdapter
import co.za.dvt.myskilldevapp.constants.HOUSE
import co.za.dvt.myskilldevapp.constants.SPELL
import co.za.dvt.myskilldevapp.databinding.ActivitySpellsBinding
import co.za.dvt.myskilldevapp.extensions.SLIDE_IN_ACTIVITY
import co.za.dvt.myskilldevapp.extensions.goToActivityWithPayload
import co.za.dvt.myskilldevapp.features.activities.BaseChildActivity
import co.za.dvt.myskilldevapp.features.houses.HouseActivity
import co.za.dvt.myskilldevapp.helpers.hideCurrentLoadingDialog
import co.za.dvt.myskilldevapp.helpers.showLoadingDialog
import co.za.dvt.myskilldevapp.models.Spell
import kotlinx.android.synthetic.main.activity_spells.*

class SpellsActivity : BaseChildActivity(), SpellsAdapter.SpellClickListener {

    private lateinit var binding: ActivitySpellsBinding
    private lateinit var spellsViewModel: SpellsViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.spells)

        var spellsRepository = SpellsRepository()
        var application = requireNotNull(this).application
        var viewModelFactory = SpellsViewModelFactory(spellsRepository, application)

        spellsViewModel = ViewModelProviders.of(this, viewModelFactory).get(SpellsViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_spells)
        binding.spellsViewModel = spellsViewModel
        binding.lifecycleOwner = this

        spellsViewModel.isBusy.observe(this, Observer { isBusy(it) })
        spellsViewModel.spells.observe(this, Observer { onSpellsSet(it) })

        spellsViewModel.getAndShowSpells()
    }

    private fun isBusy(isBusy: Boolean){
        if(isBusy)
            showLoadingDialog("please wait...", this)
        else
            hideCurrentLoadingDialog(this)
    }

    private fun onSpellsSet(spells: List<Spell?>?){
        rvSpells?.layoutManager = LinearLayoutManager(this)
        val spellsAdapter = SpellsAdapter(this, spells)
        spellsAdapter.setClickListener(this)
        rvSpells?.adapter = spellsAdapter
    }

    override fun onSpellClick(view: View, position: Int) {

        val selectedSpell = spellsViewModel.spells?.value?.get(position)

        var payload = Bundle()
        payload.putParcelable(SPELL, selectedSpell)
        goToActivityWithPayload(SpellActivity::class.java, payload, SLIDE_IN_ACTIVITY)
    }

}
