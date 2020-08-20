package co.za.dvt.myskilldevapp.features.registration

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.RegistrationViewpagerAdapter
import co.za.dvt.myskilldevapp.animations.DepthPageTransformer
import co.za.dvt.myskilldevapp.animations.ZoomOutPageTransformer
import co.za.dvt.myskilldevapp.databinding.ActivityRegistrationBinding
import co.za.dvt.myskilldevapp.extensions.TRAIL_TO
import co.za.dvt.myskilldevapp.extensions.blinkView
import co.za.dvt.myskilldevapp.extensions.goToActivityWithNoPayload
import co.za.dvt.myskilldevapp.features.activities.BaseParentActivity
import co.za.dvt.myskilldevapp.features.base.BaseFragment
import co.za.dvt.myskilldevapp.features.registration.fragments.RegistrationStep1Fragment
import co.za.dvt.myskilldevapp.features.registration.fragments.RegistrationStep2Fragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_registration.*

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
        initPager()

        pagerToolbar?.title = " CREATE ACCOUNT"
        pagerToolbar?.setLogo(R.drawable.ic_select_register_light)
    }

    private fun addObservers() {

    }

    private fun initPager() {
        var fragments = listOf<BaseFragment>(
            RegistrationStep1Fragment.newInstance("Step 1", "Please choose a user type"),
            RegistrationStep2Fragment.newInstance("Step 2", "Please enter your basic details")
        )

        var regVpAdapter = RegistrationViewpagerAdapter(fragments, this)
        vpRegistrationSteps.adapter  = regVpAdapter
        //vpRegistrationSteps.orientation = ViewPager2.ORIENTATION_VERTICAL
        vpRegistrationSteps.setPageTransformer(ZoomOutPageTransformer())

        TabLayoutMediator(tbStages, vpRegistrationSteps) {  tab, position ->
            tab.text = fragments[position].title
            txtStageDescription.text = fragments[position].description
        }.attach()
    }

    fun onNextButtonClicked(view: View){
        view.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
            vpRegistrationSteps.beginFakeDrag()
            var dragAmount = -vpRegistrationSteps.width
            vpRegistrationSteps.fakeDragBy(dragAmount.toFloat())
        }, {})

    }

    fun onPrevButtonClicked(view: View){
        view.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
            vpRegistrationSteps.beginFakeDrag()
            var dragAmount = vpRegistrationSteps.width
            vpRegistrationSteps.fakeDragBy(dragAmount.toFloat())
        }, {})
    }

}