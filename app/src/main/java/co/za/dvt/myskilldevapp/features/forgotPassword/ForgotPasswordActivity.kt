package co.za.dvt.myskilldevapp.features.forgotPassword

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.databinding.ActivityForgotPasswordBinding
import co.za.dvt.myskilldevapp.features.activities.BaseChildActivity

class ForgotPasswordActivity : BaseChildActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var application = requireNotNull(this).application
        var viewModelFactory = ForgotPasswordViewModelFactory(application)

        forgotPasswordViewModel = ViewModelProviders.of(this, viewModelFactory).get(ForgotPasswordViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        binding.forgotPasswordViewModel = forgotPasswordViewModel
        binding.lifecycleOwner = this

        addObservers()

        supportActionBar?.title = " Reset password"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_reset_pass_light)
    }

    private fun addObservers() {
        forgotPasswordViewModel.errorMessage.observe(this, Observer { onErrorMessageSet(it) })
    }

    private fun onErrorMessageSet(errorMessage: String) {

     }

    fun onRequestOTPClicked(view: View){
        forgotPasswordViewModel.checkAndFetchOtp()
    }

}