package co.za.dvt.myskilldevapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import co.za.dvt.myskilldevapp.databinding.ActivityMainBinding
import co.za.dvt.myskilldevapp.extensions.blinkView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,  R.layout.activity_main)
        binding.mainViewModel = MainViewModel()

        btnDice.setOnClickListener {
            it.blinkView(0.5f, 1.0f, 500, 2, Animation.REVERSE, 0)

            binding.invalidateAll()
            binding.mainViewModel?.showAllGood()
        }
    }
}