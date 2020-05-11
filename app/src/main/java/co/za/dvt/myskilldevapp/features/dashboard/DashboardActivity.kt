package co.za.dvt.myskilldevapp.features.dashboard

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.databinding.ActivityDashboardBinding
import co.za.dvt.myskilldevapp.extensions.FADE_IN_ACTIVITY
import co.za.dvt.myskilldevapp.extensions.blinkView
import co.za.dvt.myskilldevapp.extensions.goToActivityWithNoPayload
import co.za.dvt.myskilldevapp.extensions.rotateView
import co.za.dvt.myskilldevapp.features.activities.BaseActivity
import co.za.dvt.myskilldevapp.features.characters.CharatersActivity
import co.za.dvt.myskilldevapp.features.dashboard.fragments.CarPrizesFragment
import co.za.dvt.myskilldevapp.features.dashboard.fragments.StatsHistoryFragment
import co.za.dvt.myskilldevapp.features.database.MyGameDatabase
import co.za.dvt.myskilldevapp.features.houses.HousesActivity
import co.za.dvt.myskilldevapp.helpers.*
import co.za.dvt.myskilldevapp.models.CarModel
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var dataSource = MyGameDatabase.getInstance(application).gameStatsDAO
        var repository = DashboardRepository(dataSource)
        var application = requireNotNull(this).application
        var viewModelFactory = DashboardViewModelFactory(repository, application)

        dashboardViewModel = ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        binding.dashboardViewModel = dashboardViewModel
        binding.lifecycleOwner = this
    }

    fun onViewCharactersButtonClicked(view: View){
        goToActivityWithNoPayload(CharatersActivity::class.java, FADE_IN_ACTIVITY)
    }

    fun onViewHousesButtonClicked(view: View){
        goToActivityWithNoPayload(HousesActivity::class.java, FADE_IN_ACTIVITY)
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