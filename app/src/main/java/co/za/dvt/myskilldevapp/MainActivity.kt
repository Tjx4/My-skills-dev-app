package co.za.dvt.myskilldevapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import co.za.dvt.myskilldevapp.databinding.ActivityMainBinding
import co.za.dvt.myskilldevapp.extensions.blinkView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        Log.i("MV", "Called ViewModel providers of")
    }

    fun onRollButtonClicked(view: View) {
        view.blinkView(0.5f, 1.0f, 500, 2, Animation.REVERSE, 0)
        Log.i("MV", "onRollButtonClicked")
    }

}