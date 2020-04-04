package co.za.dvt.myskilldevapp.features.dashboard

import android.os.Bundle
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

        dashboardViewModel.winCount.observe(this, Observer { onRoundWin(it) })
        dashboardViewModel.isLuckyNumberError.observe(this, Observer { onGetLuckyNumber(it) })
        dashboardViewModel.isCarsError.observe(this, Observer { onGetCarsError(it) })
        dashboardViewModel.isBusy.observe(this, Observer { toggleIsBusy(it) })
        dashboardViewModel.rolledNumber.observe(this, Observer { showRolledDiceNumber(it) })
        dashboardViewModel.isTimeFinished.observe(this, Observer { onTimeFinished(it) })
        dashboardViewModel.availableCars.observe(this, Observer { showPrices(it) })

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        binding.dashboardViewModel = dashboardViewModel
        binding.lifecycleOwner = this
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
        dashboardViewModel.onRollCompleted()
        btnDice.isEnabled = true
    }

    private fun showRolledDiceNumber(rolledNumber: Int) {
        imgDice.setImageResource(dashboardViewModel.getRolledNumberDiceResource(rolledNumber))
    }

    private fun onTimeFinished(timeFinished: Boolean) {
        showErrorAlert(this, getString(R.string.error),  getString(R.string.out_of_time_message), getString(R.string.end_game)) {finish()}
    }

    private fun showPrices(availableCars: List<CarModel>) {
        if(availableCars.isEmpty()) return

        var carPricesFragment = CarPrizesFragment.newInstance(availableCars)
        carPricesFragment.isCancelable = false
        showDialogFragment(getString(R.string.prices_heading), R.layout.fragment_cars_list, carPricesFragment, this)
    }

    private fun onRoundWin(winCount: Int) {
        dashboardViewModel.pauseCountDown()

        when(winCount){
            dashboardViewModel.jackportTarget -> showSuccessAlert(this, getString(R.string.congratulations),  getString(R.string.jackport_message),getString(R.string.view_prices), ::onViewPricesClicked)
            else -> showSuccessAlert(this,getString(R.string.you_win), getString(R.string.round_victory_message, dashboardViewModel.currentLuckyNumber.value), getString(R.string.play_again), ::onRestartGameClicked)
        }
    }

    private fun onViewPricesClicked() {
        dashboardViewModel.fetchCarPrices()
    }

    private fun onRestartGameClicked() {
        dashboardViewModel.resetGame()
    }

    private fun onGetLuckyNumber(isError: Boolean) {
        if(isError)
            showCancellableErrorAlert(this, getString(R.string.error), getString(R.string.lucky_number_error_message) , getString(R.string.try_again), getString(R.string.close_app), {dashboardViewModel.startNewRound()}, ::finish)
    }

    private fun onGetCarsError(isError: Boolean) {
        showCancellableErrorAlert(this, getString(R.string.error), getString(R.string.cars_error_message), getString(R.string.try_again), getString(R.string.close_app), { dashboardViewModel.fetchCarPrices()}, ::finish)
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

    fun onPriceItemClick(car: CarModel) {
        var selectedPrice = "${car?.brand}  ${car?.model}"
        showGameWin(selectedPrice)

        dashboardViewModel?.availableCars.value = ArrayList()
    }

    fun showGameWin(jackpotPrice: String){
        dashboardViewModel?.setJackpotPrice(jackpotPrice)
        showSuccessAlert(this, getString(R.string.game_completed), getString(R.string.jackport_price_message, jackpotPrice), getString(R.string.finish_game), ::onFinishGameClicked)
    }

    private fun onFinishGameClicked() {
        finish()
    }

}