package co.za.dvt.myskilldevapp.features.houses

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.CarPrizesAdapter
import co.za.dvt.myskilldevapp.adapters.HousesAdapter
import co.za.dvt.myskilldevapp.databinding.ActivityHousesBinding
import co.za.dvt.myskilldevapp.features.activities.BaseActivity
import co.za.dvt.myskilldevapp.helpers.hideCurrentLoadingDialog
import co.za.dvt.myskilldevapp.helpers.showLoadingDialog
import co.za.dvt.myskilldevapp.helpers.showShortToast
import co.za.dvt.myskilldevapp.models.House
import kotlinx.android.synthetic.main.activity_houses.*
import java.util.ArrayList

class HousesActivity : BaseActivity(), HousesAdapter.HouseClickListener {
    private lateinit var binding: ActivityHousesBinding
    private lateinit var housesViewModel: HousesViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.houses)

        var housesRepository = HousesRepository()
        var application = requireNotNull(this).application
        var viewModelFactory = HousesViewModelFactory(housesRepository, application)

        housesViewModel = ViewModelProviders.of(this, viewModelFactory).get(HousesViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_houses)
        binding.housesViewModel = housesViewModel
        binding.lifecycleOwner = this

        housesViewModel.isBusy.observe(this, Observer { isBusy(it) })
        housesViewModel.houses.observe(this, Observer { onHousesSet(it) })
    }

    private fun isBusy(isBusy: Boolean){
        if(isBusy)
            showLoadingDialog("please wait...", this)
        else
            hideCurrentLoadingDialog(this)
    }

    private fun onHousesSet(houses: List<House?>?){
        rvHouses?.layoutManager = LinearLayoutManager(this)
        val housesAdapter = HousesAdapter(this, houses)
        housesAdapter.setClickListener(this)
        rvHouses?.adapter = housesAdapter
    }

    fun onGetHousesbuttonClicked(view: View){
        housesViewModel.getAndShowHouses()
    }

    override fun onHouseClick(view: View, position: Int) {
        showShortToast("${housesViewModel.houses?.value?.get(position)?.name}", this)
    }
}
