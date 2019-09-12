package co.za.dvt.myskilldevapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import co.za.dvt.myskilldevapp.databinding.ActivityMainBinding
import co.za.dvt.myskilldevapp.extensions.blinkView
import co.za.dvt.myskilldevapp.extensions.rotateView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.mainViewModel = mainViewModel

        mainViewModel.setNewLuckyNumber()
        binding.luckyNumber = mainViewModel.luckyNumber


        Log.i("MV", "Called ViewModel providers of")
    }

    fun onRollButtonClicked(view: View) {
        view.blinkView(0.5f, 1.0f, 500, 2, Animation.REVERSE, 0)
        imgDice.rotateView(0f, 180f, 0.5f, 0.5f,300, 2, Animation.REVERSE, 0, ::onRotateDone, ::onRotateStart)

        Log.i("MV", "onRollButtonClicked")
    }

    private fun onRotateDone() {
        imgDice.rotateView(0f, 180f, 0.5f, 0.5f,500, 2, Animation.REVERSE, 0, ::onRollComplete)
    }

    private fun onRotateStart() {
        btnDice.isEnabled = false

        binding.invalidateAll()
        mainViewModel.getLuckyNumber()
        binding.luckyNumber = mainViewModel.luckyNumber

        Log.i("MV", "onRotateStart...")
    }

    private fun onRollComplete() {
        binding.invalidateAll()
        mainViewModel.onLuckyNumberRetrieved()
        showRolledNumber(mainViewModel.rolledNumber)

        if(mainViewModel.isWin){
            mainViewModel.resetGame()
            Toast.makeText(this, "You won :)", Toast.LENGTH_SHORT).show()
        }

        btnDice.isEnabled = true

        Log.i("MV", "onRollComplete...")
    }

    private fun showRolledNumber(rolledNumber:Int) {
        var diceImageRes = 1
        when(rolledNumber){
            1 -> diceImageRes = R.mipmap.ic_dice_1
            2 ->  diceImageRes = R.mipmap.ic_dice_2
            3 ->  diceImageRes = R.mipmap.ic_dice_3
            4 ->  diceImageRes = R.mipmap.ic_dice_4
            5 ->  diceImageRes = R.mipmap.ic_dice_5
            else ->  diceImageRes = R.mipmap.ic_dice_6
        }

        imgDice.setImageResource(diceImageRes)
    }


}