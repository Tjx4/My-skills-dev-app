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
import co.za.dvt.myskilldevapp.extensions.blinkView
import co.za.dvt.myskilldevapp.extensions.rotateView
import co.za.dvt.myskilldevapp.features.activities.BaseActivity
import co.za.dvt.myskilldevapp.features.dashboard.fragments.CarPrizesFragment
import co.za.dvt.myskilldevapp.features.dashboard.fragments.StatsHistoryFragment
import co.za.dvt.myskilldevapp.features.database.MyGameDatabase
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

        addObservers()
    }

    private fun addObservers() {
        dashboardViewModel.winCount.observe(this, Observer { onWinRound(it) })
        dashboardViewModel.luckyNumberError.observe(this, Observer { onGetLuckyNumberError(it) })
        dashboardViewModel.carsError.observe(this, Observer { onGetCarsError(it) })
        dashboardViewModel.isBusy.observe(this, Observer { toggleIsBusy(it) })
        dashboardViewModel.rolledNumber.observe(this, Observer { showRolledDiceNumber(it) })
        dashboardViewModel.isTimeFinished.observe(this, Observer { onTimeFinished(it) })
        dashboardViewModel.availableCars.observe(this, Observer { showPrices(it) })
    }

    fun onRollButtonClicked(view: View) {
        view.blinkView(0.5f, 1.0f, 500, 2, Animation.REVERSE, 0)
        imgDice.rotateView(0f, 360f, 0.5f, 0.5f,600, 2, Animation.ABSOLUTE, 0, ::slowDownDiceRoll, ::onRotateStart)
    }

    private fun slowDownDiceRoll() {
        imgDice.rotateView(0f, 360f, 0.5f, 0.5f,900, 2, Animation.REVERSE, 0, ::onRollComplete)
    }

    private fun onRotateStart() {
        dashboardViewModel.activityMessage.value = getString(R.string.rolling)
        btnDice.isEnabled = false
    }

    private fun onRollComplete() {
        dashboardViewModel.onRollCompleted((1..6).random())
        btnDice.isEnabled = true
    }

    private fun showRolledDiceNumber(rolledNumber: Int) {
        val di = when(rolledNumber){
            1 -> R.drawable.ic_di_1
            2 -> R.drawable.ic_di_2
            3 -> R.drawable.ic_di_3
            4 -> R.drawable.ic_di_4
            5 -> R.drawable.ic_di_5
            else -> R.drawable.ic_di_6
        }
        imgDice.setImageResource(di)
    }

    private fun onTimeFinished(timeFinished: Boolean) {
        clCParent.visibility = View.INVISIBLE
        showCancellableErrorAlert(this, getString(R.string.time_out), getString(R.string.out_of_time_message), getString(R.string.try_again), getString(R.string.end_game), ::onResetGameClicked, ::finish)
    }

    private fun onResetGameClicked() {
        dashboardViewModel.resetGame()
    }

    private fun showPrices(availableCars: List<CarModel>) {
        clCParent.visibility = View.INVISIBLE
        var carPricesFragment = CarPrizesFragment.newInstance(availableCars)
        carPricesFragment.isCancelable = false
        showDialogFragment(getString(R.string.prices_heading), R.layout.fragment_cars_list, carPricesFragment, this)
    }

    private fun onWinRound(winCount: Int) {
        if(winCount < 1) return
        dashboardViewModel.pauseCountDown()

        when(winCount){
            dashboardViewModel.maxRounds -> showSuccessAlert(this, getString(R.string.congratulations), getString(R.string.jackport_message), getString(R.string.view_prices), ::onViewPricesClicked)
            else -> showSuccessAlert(this,getString(R.string.you_win), getString(R.string.round_victory_message, dashboardViewModel.currentLuckyNumber.value), getString(R.string.play_again)) {dashboardViewModel.startNewRound()}
        }
    }

    private fun onViewPricesClicked() {
        dashboardViewModel.fetchAndSetJackportPrices()
    }

    private fun onGetLuckyNumberError(errorMessage: String) {
        clCParent.visibility = View.INVISIBLE
        showCancellableErrorAlert(this, getString(R.string.error), errorMessage, getString(R.string.try_again), getString(R.string.close_app), {dashboardViewModel.startNewRound()}, ::finish)
    }

    private fun onGetCarsError(errorMessage: String) {
        clCParent.visibility = View.INVISIBLE
        showCancellableErrorAlert(this, getString(R.string.error), errorMessage, getString(R.string.try_again), getString(R.string.close_app), { dashboardViewModel.fetchAndSetJackportPrices()}, ::finish)
    }

    private fun toggleIsBusy(isBusy: Boolean) {
       if(isBusy) {
           showLoadingDialog(dashboardViewModel.busyMessage, this)
           clCParent.visibility = View.INVISIBLE
       }else {
           hideCurrentLoadingDialog(this)
           clCParent.visibility = View.VISIBLE
       }
    }

    fun onPriceItemClick(selectedPrice: String) {
        dashboardViewModel?.recordGameStats(selectedPrice)
        showSuccessAlert(this, getString(R.string.game_completed), getString(R.string.jackport_price_message, selectedPrice), getString(R.string.finish_game), ::finish)
    }

    fun onShowStatsHistoryClicked() {
        dashboardViewModel?.busyMessage = getString(R.string.fetch_stats)
        dashboardViewModel.pauseCountDown()
        var statsHistoryFragment = StatsHistoryFragment.newInstance()
        statsHistoryFragment.isCancelable = true
        showDialogFragment(getString(R.string.stats_history), R.layout.fragment_stats_history, statsHistoryFragment, this)
    }

    fun onStatsClose() {
        dashboardViewModel.continueCountDown()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_history -> onShowStatsHistoryClicked()
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