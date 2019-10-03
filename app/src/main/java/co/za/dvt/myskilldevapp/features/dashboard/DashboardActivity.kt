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
import co.za.dvt.myskilldevapp.models.Car
import co.za.dvt.myskilldevapp.models.LuckyNumber
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var repository = DashboardRepository()
        var dataSource = MyGameDatabase.getInstance(application).gameStatsDAO
        var application = requireNotNull(this).application
        var viewModelFactory = DashboardViewModelFactory(repository, dataSource, application)
        dashboardViewModel = ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)

        dashboardViewModel.isWin.observe(this, Observer { onGameStatusChanged(it) })
        dashboardViewModel.isError.observe(this, Observer { onGetLuckyNumber(it) })
        dashboardViewModel.isCarsError.observe(this, Observer { onGetCars(it) })
        dashboardViewModel.isBusy.observe(this, Observer { toggleIsBusy(it) })
        dashboardViewModel.rolledNumber.observe(this, Observer { showRolledNumber(it) })
        dashboardViewModel.luckyNumberModel.observe(this, Observer { onLuckyNumberModelChanged(it) })
        dashboardViewModel.availableCars.observe(this, Observer { onAvailableCarsChanged(it) })

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        binding.dashboardViewModel = dashboardViewModel
        binding.lifecycleOwner = this
    }

    fun onRollButtonClicked(view: View) {
        view.blinkView(0.5f, 1.0f, 500, 2, Animation.REVERSE, 0)
        imgDice.rotateView(0f, 360f, 0.5f, 0.5f,600, 2, Animation.ABSOLUTE, 0, ::onInitialRotateDone, ::onRotateStart)
    }

    private fun onInitialRotateDone() {
        imgDice.rotateView(0f, 360f, 0.5f, 0.5f,900, 2, Animation.REVERSE, 0, ::onRollComplete)
    }

    private fun onRotateStart() {
        btnDice.isEnabled = false
        dashboardViewModel.rollDice()
    }

    private fun onRollComplete() {
        dashboardViewModel.onRollCompleted()
        btnDice.isEnabled = true
    }

    private fun showRolledNumber(rolledNumber:Int) {
        imgDice.setImageResource(dashboardViewModel.getRolledNumberDi(rolledNumber))
    }

    private fun onLuckyNumberModelChanged(luckyNumber: LuckyNumber?) {
        if(luckyNumber == null){
            dashboardViewModel.onLuckyNumnerError()
        }
        else{
            dashboardViewModel.setLuckyNumber(luckyNumber.luckyNumber)
        }
    }

    private fun onAvailableCarsChanged(availableCars:List<Car>?) {
        if(availableCars == null){
            dashboardViewModel.onAvailableCarsError()
        }
        else{
            if(availableCars.isNotEmpty()){
                showPrices(availableCars)
            }

        }
    }

    private fun showPrices(availableCars: List<Car>) {
        var carPricesFragment = CarPrizesFragment.newInstance()
        carPricesFragment.isCancelable = false
        showDialogFragment("", R.layout.fragment_cars_list, carPricesFragment, this)
    }

    private fun onGameStatusChanged(isWin: Boolean) {
        if(isWin){
            dashboardViewModel.incrimentWin()
            dashboardViewModel.resetMessage()

            when(dashboardViewModel.winCount){
                2 -> {
                    showSuccessAlert(this,getString(R.string.congratulations),  "You've won the Jackpot, you can now select from our list of a prices"
                        ,getString(R.string.view_prices), ::onViewPricesClicked)
                }
                else -> {

                    showSuccessAlert(this,getString(R.string.you_win),  "${dashboardViewModel.currentLuckyNumber.value} is your lucky number you've won this round... please roll again to win more"
                        ,getString(R.string.play_again), ::onRestartGameClicked)
                }

            }
        }
    }

    private fun onViewPricesClicked() {
        dashboardViewModel.showPrices()
    }

    private fun onRestartGameClicked() {
        dashboardViewModel.resetGame()
    }


    private fun onGetLuckyNumber(isError: Boolean) {
        if(isError){
            showCancellableErrorAlert(this, getString(R.string.error),  "Error getting lucky number :(", "Try again", "Close app", {dashboardViewModel.fetchLuckyNumber()}, ::finish)
        }
    }

    private fun onGetCars(isError: Boolean) {
        if(isError){
            showCancellableErrorAlert(this, getString(R.string.error),  "Error getting cars :(", "Try again","Close app", {dashboardViewModel.fetchCars()}, ::finish)
        }
    }

    private fun toggleIsBusy(isBusy: Boolean) {
       if(isBusy){
           showLoadingDialog("Starting game please wait...", this)
       }
        else{
           hideLoadingDialog(this)
       }
    }

    fun showGameWin(jackpotPrice: String){
        dashboardViewModel?.setJackpotPrice(jackpotPrice)

        showSuccessAlert(this,getString(R.string.game_completed),  "You chose the $jackpotPrice as your prize, you will be contacted soon to collect your price"
            ,getString(R.string.finish_game), ::onFinishGameClicked)
    }

    private fun onFinishGameClicked() {
        finish()
    }

}