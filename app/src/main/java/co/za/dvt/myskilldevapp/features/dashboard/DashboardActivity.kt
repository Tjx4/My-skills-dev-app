package co.za.dvt.myskilldevapp.features.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.databinding.ActivityMainBinding
import co.za.dvt.myskilldevapp.extensions.blinkView
import co.za.dvt.myskilldevapp.extensions.rotateView
import kotlinx.android.synthetic.main.activity_main.*

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        dashboardViewModel.isWin.observe(this, Observer { onGameStatusChanged(it) })
        dashboardViewModel.isError.observe(this, Observer { onGetLuckyNumber(it) })
        dashboardViewModel.isBusy.observe(this, Observer { toggleIsBusy(it) })
        dashboardViewModel.rolledNumber.observe(this, Observer { showRolledNumber(it) })

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.dashboardViewModel = dashboardViewModel
        binding.lifecycleOwner = this
    }

    fun onRollButtonClicked(view: View) {
        view.blinkView(0.5f, 1.0f, 500, 2, Animation.REVERSE, 0)
        imgDice.rotateView(0f, 180f, 0.5f, 0.5f,300, 2, Animation.REVERSE, 0, ::onInitialRotateDone, ::onRotateStart)
    }

    private fun onInitialRotateDone() {
        imgDice.rotateView(0f, 180f, 0.5f, 0.5f,500, 2, Animation.REVERSE, 0, ::onRollComplete)
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

    private fun onGameStatusChanged(isWin: Boolean) {
        if(isWin){
            dashboardViewModel.showWin()
            Toast.makeText(this, "You won :)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onRestartGameClicked(veiw: View) {
        dashboardViewModel.resetGame()
    }

    private fun onGetLuckyNumber(isError: Boolean) {
        if(isError)
            Toast.makeText(this, "Error getting lucky number :(", Toast.LENGTH_SHORT).show()
    }

    private fun toggleIsBusy(isBusy: Boolean) {
       if(isBusy){
           // Show loader
       }
        else{
           // hide loader
       }
    }

}