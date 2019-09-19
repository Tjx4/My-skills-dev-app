package co.za.dvt.myskilldevapp.features.dashboard

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.databinding.ActivityMainBinding
import co.za.dvt.myskilldevapp.extensions.blinkView
import co.za.dvt.myskilldevapp.extensions.rotateView
import co.za.dvt.myskilldevapp.features.activities.BaseActivity
import co.za.dvt.myskilldevapp.helpers.hideLoadingDialog
import co.za.dvt.myskilldevapp.helpers.showErrorAlert
import co.za.dvt.myskilldevapp.helpers.showLoadingDialog
import co.za.dvt.myskilldevapp.helpers.showSuccessAlert
import co.za.dvt.myskilldevapp.models.Car
import co.za.dvt.myskilldevapp.models.LuckyNumberModel
import kotlinx.android.synthetic.main.activity_main.*

class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        dashboardViewModel.isWin.observe(this, Observer { onGameStatusChanged(it) })
        dashboardViewModel.isError.observe(this, Observer { onGetLuckyNumber(it) })
        dashboardViewModel.isBusy.observe(this, Observer { toggleIsBusy(it) })
        dashboardViewModel.rolledNumber.observe(this, Observer { showRolledNumber(it) })
        dashboardViewModel.luckyNumberModel.observe(this, Observer { onLuckyNumberModelChanged(it) })
        dashboardViewModel.availableCars.observe(this, Observer { onAvailableCarsChanged(it) })

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
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

    private fun onLuckyNumberModelChanged(luckyNumberModel:LuckyNumberModel) {
        if(luckyNumberModel != null){
            dashboardViewModel.setLuckyNumber()
        }
        else{
            dashboardViewModel.onConnectionError()
        }
    }

    private fun onAvailableCarsChanged(availableCars:List<Car>) {
        if(availableCars == null){
            dashboardViewModel.onConnectionError()
        }
    }

    private fun onGameStatusChanged(isWin: Boolean) {
        if(isWin){
            dashboardViewModel.resetMessage()
            showSuccessAlert(this,"You win",  "${dashboardViewModel.currentLuckyNumber.value} is your lucky number you've won this round... please roll again to win more"
                ,"Play again", ::onRestartGameClicked)
        }
    }

    private fun onRestartGameClicked() {
        dashboardViewModel.resetGame()
    }

    private fun onGetLuckyNumber(isError: Boolean) {
        if(isError){
            showErrorAlert(this, "Error",  "Error getting lucky number :(", "Try again")
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

}