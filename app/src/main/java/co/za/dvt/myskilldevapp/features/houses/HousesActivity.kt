package co.za.dvt.myskilldevapp.features.houses

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.databinding.ActivityHousesBinding
import co.za.dvt.myskilldevapp.features.activities.BaseActivity


class HousesActivity : BaseActivity() {
    private lateinit var binding: ActivityHousesBinding
    private lateinit var housesViewModel: HousesViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var housesRepository = HousesRepository()
        var application = requireNotNull(this).application
        var viewModelFactory = HousesViewModelFactory(housesRepository, application)

        housesViewModel = ViewModelProviders.of(this, viewModelFactory).get(HousesViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_houses)
        binding.housesViewModel = housesViewModel
        binding.lifecycleOwner = this
    }

    fun onGetHousesbuttonClicked(view: View){
        housesViewModel.getAndShowHouses()
    }
}
