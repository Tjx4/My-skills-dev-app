package co.za.dvt.myskilldevapp.features.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        dashboardViewModel.getLuckyNumber()

        dashboardViewModel.isWin.observe(this, Observer { onGameStatusChanged(it) })
        dashboardViewModel.isError.observe(this, Observer { onGetLuckyNumber(it) })
        dashboardViewModel.isBusy.observe(this, Observer { toggleIsBusy(it) })

        binding.mainViewModel = dashboardViewModel // get direct value instead of trough viewModel
        binding.luckyNumber = dashboardViewModel.luckyNumber
    }

    fun onRollButtonClicked(view: View) {
        view.blinkView(0.5f, 1.0f, 500, 2, Animation.REVERSE, 0)
        imgDice.rotateView(0f, 180f, 0.5f, 0.5f,300, 2, Animation.REVERSE, 0, ::onRotateDone, ::onRotateStart)
    }

    private fun onRotateDone() {
        imgDice.rotateView(0f, 180f, 0.5f, 0.5f,500, 2, Animation.REVERSE, 0, ::onRollComplete)
    }

    private fun onRotateStart() {
        btnDice.isEnabled = false

        binding.invalidateAll()
        binding.luckyNumber = dashboardViewModel.luckyNumber

        Log.i("MV", "onRotateStart...")
    }

    private fun onRollComplete() {
        binding.invalidateAll()
        dashboardViewModel.onLuckyNumberRetrieved()
        showRolledNumber(dashboardViewModel.rolledNumber)
        btnDice.isEnabled = true

        Log.i("MV", "onRollComplete...")
    }

    private fun onGameStatusChanged(isWin: Boolean) {
        if(isWin){
            dashboardViewModel.resetGame()
            Toast.makeText(this, "You won :)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onGetLuckyNumber(isError: Boolean) {
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

    private fun showRolledNumber(rolledNumber:Int) {
        var diceImageRes = when(rolledNumber){
            1 -> R.mipmap.ic_dice_1
            2 -> R.mipmap.ic_dice_2
            3 -> R.mipmap.ic_dice_3
            4 -> R.mipmap.ic_dice_4
            5 -> R.mipmap.ic_dice_5
            else -> R.mipmap.ic_dice_6
        }

        imgDice.setImageResource(diceImageRes)
    }

}