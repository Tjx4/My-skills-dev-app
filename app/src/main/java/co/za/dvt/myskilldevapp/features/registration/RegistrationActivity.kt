package co.za.dvt.myskilldevapp.features.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.databinding.ActivityRegistrationBinding
import co.za.dvt.myskilldevapp.features.activities.BaseParentActivity

class RegistrationActivity : BaseParentActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var application = requireNotNull(this).application
        var viewModelFactory = RegistrationViewModelFactory(application)

        registrationViewModel = ViewModelProviders.of(this, viewModelFactory).get(RegistrationViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        binding.registrationViewModel = registrationViewModel
        binding.lifecycleOwner = this

        addObservers()

        supportActionBar?.title = " CREATE ACCOUNT"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_select_register_light)
    }

    private fun addObservers() {

    }
}
