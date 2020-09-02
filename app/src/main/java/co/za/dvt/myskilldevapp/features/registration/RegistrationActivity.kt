package co.za.dvt.myskilldevapp.features.registration

import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.RegistrationViewpagerAdapter
import co.za.dvt.myskilldevapp.databinding.ActivityRegistrationBinding
import co.za.dvt.myskilldevapp.enums.UserTypes
import co.za.dvt.myskilldevapp.extensions.*
import co.za.dvt.myskilldevapp.features.activities.BaseParentActivity
import co.za.dvt.myskilldevapp.features.base.BaseRegistrationFragment
import co.za.dvt.myskilldevapp.features.login.LoginActivity
import co.za.dvt.myskilldevapp.features.registration.fragments.RegistrationFinalizeFragment
import co.za.dvt.myskilldevapp.features.registration.fragments.RegistrationPersonalDetailsFragment
import co.za.dvt.myskilldevapp.features.registration.fragments.RegistrationStep1Fragment
import co.za.dvt.myskilldevapp.helpers.hideCurrentLoadingDialog
import co.za.dvt.myskilldevapp.helpers.showLoadingDialog
import co.za.dvt.myskilldevapp.helpers.showSuccessAlert
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : BaseParentActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    lateinit var registrationViewModel: RegistrationViewModel
    lateinit var fragments: List<BaseRegistrationFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var application = requireNotNull(this).application
        var viewModelFactory = RegistrationViewModelFactory(application)

        registrationViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            RegistrationViewModel::class.java
        )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        binding.registrationViewModel = registrationViewModel
        binding.lifecycleOwner = this

        addObservers()
        initPager()

        pagerToolbar?.title = " CREATE ACCOUNT"
        pagerToolbar?.setLogo(R.drawable.ic_create_account_light)
        setSupportActionBar(pagerToolbar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
    }

    private fun addObservers() {
        registrationViewModel.showLoading.observe(this, Observer { toggleShowLoading(it) })
        registrationViewModel.userType.observe(this, Observer { onUserTypeSet(it) })
        registrationViewModel.surname.observe(this, Observer { onSurnameChanged(it) })
        registrationViewModel.mobileNumber.observe(this, Observer { onMobileChanged(it) })
        registrationViewModel.errorMessage.observe(this, Observer { onErrorMessageSet(it) })
        registrationViewModel.isRegistered.observe(this, Observer { onAccountCreated(it) })
    }

    private fun initPager() {
        fragments = listOf<BaseRegistrationFragment>(
            RegistrationStep1Fragment.newInstance("Step 1", "Please choose a user type", true),
            RegistrationPersonalDetailsFragment.newInstance(
                "Step 2",
                "Please enter your personal information",
                false
            ),
            RegistrationFinalizeFragment.newInstance("Step 3", "Please confirm your details", false)
        )

        vpRegistrationSteps.isUserInputEnabled = false

        var regVpAdapter = RegistrationViewpagerAdapter(fragments, this)
        vpRegistrationSteps.adapter  = regVpAdapter
        var context = this
        var lastPos = 0
        vpRegistrationSteps.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

                var touchableList = (tbStages.getChildAt(0) as LinearLayout).children
                var index = 0
                touchableList?.forEach {
                    it.isEnabled = fragments[index].isEnabled
                    index++
                }

                //TOdo: Show user that tab is not yet active
               // showShortToast("Please finish previous step first", context)

                super.onPageSelected(position)
                txtStageDescription.text = fragments[position].description

                lastPos = position
            }
        })

        TabLayoutMediator(tbStages, vpRegistrationSteps) { tab, position ->
            tab.text = fragments[position].title
        }.attach()
    }

    private fun onAccountCreated(errorMessage: Boolean) {
        showSuccessAlert(this, "Success", "Congratulations ${registrationViewModel.name.value} you are now a member", "SignIn") {
            goToActivityWithNoPayload(LoginActivity::class.java, FADE_IN_ACTIVITY)
            finish()
        }
    }

    private fun onErrorMessageSet(errorMessage: String) {
        hideCurrentLoadingDialog(this)
        clErrorContainer.visibility = View.VISIBLE
        clErrorContainer.blinkView(0.6f, 1.0f, 500, 2, Animation.ABSOLUTE, 0)
    }

    private fun onUserTypeSet(userType: UserTypes){
        vpRegistrationSteps.setCurrentItem(1, true)
    }

    // Todo: Mock data binding if it comes to it
    private fun onSurnameChanged(surname: String){
        var sn = surname
    }
    private fun onMobileChanged(mobile: String){
        var sn = mobile
    }

    private fun toggleShowLoading(isBusy: Boolean) {
        showLoadingDialog(registrationViewModel.busyMessage, this)
    }

    fun enablePersonalDetailsStep() = enableStep(1)
    fun enableFinalizeStep() = enableStep(2)

    fun onRegisterButtonClicked(view: View){
        registrationViewModel.checkAndRegisterUser()
    }

    private fun enableStep(step: Int){
        fragments[step].isEnabled = true
    }

    fun moveToFinalStep(){
        vpRegistrationSteps.setCurrentItem(2, true)
    }

    fun onNextButtonClicked(view: View){
        view.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
            goToPreviousStep()
        }, {})
    }

    fun onPrevButtonClicked(view: View){
        view.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
            goToNextStep()
        }, {})
    }

    private fun goToPreviousStep() {
        val prevPosition = vpRegistrationSteps.currentItem - 1
        vpRegistrationSteps.setCurrentItem(prevPosition, true)
    }

    private fun goToNextStep() {
        val nextPosition = vpRegistrationSteps.currentItem + 1
        vpRegistrationSteps.setCurrentItem(nextPosition, true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_login -> goToActivityWithNoPayload(LoginActivity::class.java, TRAIL_FROM)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.registration_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK && vpRegistrationSteps.currentItem < 1) {
            moveTaskToBack(true)
            return true
        }
        else{
            goToPreviousStep()
        }
        return false
    }
}