package co.za.dvt.myskilldevapp.features.dashboard

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.FeaturesAdapter
import co.za.dvt.myskilldevapp.constants.SPELL
import co.za.dvt.myskilldevapp.databinding.ActivityDashboardBinding
import co.za.dvt.myskilldevapp.enums.AppFeatures
import co.za.dvt.myskilldevapp.extensions.*
import co.za.dvt.myskilldevapp.features.activities.BaseActivity
import co.za.dvt.myskilldevapp.features.characters.CharatersActivity
import co.za.dvt.myskilldevapp.features.database.MyGameDatabase
import co.za.dvt.myskilldevapp.features.houses.HousesActivity
import co.za.dvt.myskilldevapp.features.spells.SpellActivity
import co.za.dvt.myskilldevapp.features.spells.SpellsActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity(),  FeaturesAdapter.FeatureClickListener{
    private lateinit var binding: ActivityDashboardBinding
    lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setIcon(R.mipmap.ic_launcher_round)

        var dataSource = MyGameDatabase.getInstance(application).gameStatsDAO
        var repository = DashboardRepository(dataSource)
        var application = requireNotNull(this).application
        var viewModelFactory = DashboardViewModelFactory(repository, application)

        dashboardViewModel = ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        binding.dashboardViewModel = dashboardViewModel
        binding.lifecycleOwner = this


        var features = AppFeatures.values()
        rvFeatures?.layoutManager = GridLayoutManager(this, 2)
        val featuresAdapter =  FeaturesAdapter(this, features)
        featuresAdapter.setClickListener(this)
        rvFeatures?.adapter = featuresAdapter
    }

    override fun onFeatureClick(view: View, position: Int) {
        view.blinkView(0.5f, 1.0f, 400, 2, Animation.ABSOLUTE, 0, {
            when(AppFeatures.values()[position]){
                AppFeatures.Characters -> goToActivityWithNoPayload(CharatersActivity::class.java, FADE_IN_ACTIVITY)
                AppFeatures.Houses -> goToActivityWithNoPayload(HousesActivity::class.java, FADE_IN_ACTIVITY)
                AppFeatures.Spells -> goToActivityWithNoPayload(SpellsActivity::class.java, FADE_IN_ACTIVITY)
            }
        }, {})
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
          //  R.id.action_history -> onShowStatsHistoryClicked()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true)
            return super.onKeyDown(keyCode, event)
        }

        return true
    }

}