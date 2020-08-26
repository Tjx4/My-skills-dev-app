package co.za.dvt.myskilldevapp.features.dashboard

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.constants.PAYLOAD_KEY
import co.za.dvt.myskilldevapp.constants.USER
import co.za.dvt.myskilldevapp.databinding.ActivityDashboardBinding
import co.za.dvt.myskilldevapp.extensions.FADE_OUT_ACTIVITY
import co.za.dvt.myskilldevapp.extensions.goToActivityWithNoPayload
import co.za.dvt.myskilldevapp.features.activities.BaseActivity
import co.za.dvt.myskilldevapp.features.login.LoginActivity
import co.za.dvt.myskilldevapp.helpers.*
import co.za.dvt.myskilldevapp.models.UserModel
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var application = requireNotNull(this).application
        var viewModelFactory = DashboardViewModelFactory(application)

        dashboardViewModel = ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        binding.dashboardViewModel = dashboardViewModel
        binding.lifecycleOwner = this

        addObservers()

        var userName = intent.extras?.getBundle(PAYLOAD_KEY)?.getParcelable<UserModel>(USER)

        var ab = supportActionBar
        ab?.title = "Hi ${userName?.name?:"user"}"

        loadImageFromInternet(this,  userName?.picUrl!!, imgProf, R.drawable.ic_pa_light)
    }

    private fun addObservers() {
        dashboardViewModel.showLoading.observe(this, Observer { toggleShow(it) })
        dashboardViewModel.showContent.observe(this, Observer { toggleShowContent(it) })
    }

    private fun toggleShow(isBusy: Boolean) {
        clCParent.visibility = View.INVISIBLE
        showLoadingDialog(dashboardViewModel.busyMessage, this)
    }

    private fun toggleShowContent(showContent: Boolean) {
        hideCurrentLoadingDialog(this)
        clCParent.visibility = View.VISIBLE
    }

    private fun signOut() {
        goToActivityWithNoPayload(LoginActivity::class.java, FADE_OUT_ACTIVITY)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
          R.id.action_sign_out -> signOut()
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