package co.za.dvt.myskilldevapp.features.registration

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.RegistrationViewpagerAdapter
import co.za.dvt.myskilldevapp.adapters.UserTypeAdapter
import co.za.dvt.myskilldevapp.databinding.ActivityRegistrationBinding
import co.za.dvt.myskilldevapp.enums.UserTypes
import co.za.dvt.myskilldevapp.extensions.blinkView
import co.za.dvt.myskilldevapp.features.activities.BaseParentActivity
import co.za.dvt.myskilldevapp.features.base.BaseRegistrationFragment
import co.za.dvt.myskilldevapp.features.registration.fragments.RegistrationStep1Fragment
import co.za.dvt.myskilldevapp.features.registration.fragments.RegistrationStep2Fragment
import co.za.dvt.myskilldevapp.features.registration.fragments.RegistrationStep3Fragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : BaseParentActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    lateinit var registrationViewModel: RegistrationViewModel

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
        registrationViewModel.userType.observe(this, Observer { onUserTypeSet(it)})
    }

    private fun initPager() {
        var fragments = listOf<BaseRegistrationFragment>(
            RegistrationStep1Fragment.newInstance("Step 1", "Please choose a user type"),
            RegistrationStep2Fragment.newInstance("Step 2", "Please enter your basic information"),
            RegistrationStep3Fragment.newInstance("Step 3", "Finalize your details")
        )

        vpRegistrationSteps.isUserInputEnabled = false

        var regVpAdapter = RegistrationViewpagerAdapter(fragments, this)
        vpRegistrationSteps.adapter  = regVpAdapter
        //vpRegistrationSteps.orientation = ViewPager2.ORIENTATION_VERTICAL
        //vpRegistrationSteps.setPageTransformer(ZoomOutPageTransformer())
        vpRegistrationSteps.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                txtStageDescription.text = fragments[position].description
            }
        })

        TabLayoutMediator(tbStages, vpRegistrationSteps) {  tab, position ->
            tab.text = fragments[position].title
        }.attach()
    }

    private fun onUserTypeSet(userType: UserTypes){
        vpRegistrationSteps.setCurrentItem(1, true)
    }

    fun moveToFinalStep(){
        vpRegistrationSteps.setCurrentItem(2, true)
    }

    fun onNextButtonClicked(view: View){
        view.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
            goToPreviouseStep()
        }, {})

    }

    fun onPrevButtonClicked(view: View){
        view.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
            goToNextStep()
        }, {})
    }

    private fun goToPreviouseStep() {
        val prevPosition = vpRegistrationSteps.currentItem - 1
        vpRegistrationSteps.setCurrentItem(prevPosition, true)
    }

    private fun goToNextStep() {
        //vpRegistrationSteps.beginFakeDrag()
        //var dragAmount = vpRegistrationSteps.width
        //vpRegistrationSteps.fakeDragBy(dragAmount.toFloat())
        val nextPosition = vpRegistrationSteps.currentItem + 1
        vpRegistrationSteps.setCurrentItem(nextPosition, true)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK && vpRegistrationSteps.currentItem < 1) {
            moveTaskToBack(true)
            return true
        }
        else{
            goToPreviouseStep()
        }
        return false
    }
}