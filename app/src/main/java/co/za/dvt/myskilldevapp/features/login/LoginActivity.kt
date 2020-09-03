package co.za.dvt.myskilldevapp.features.login

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.constants.USER
import co.za.dvt.myskilldevapp.databinding.ActivityLoginBinding
import co.za.dvt.myskilldevapp.extensions.*
import co.za.dvt.myskilldevapp.features.activities.BaseParentActivity
import co.za.dvt.myskilldevapp.features.dashboard.DashboardActivity
import co.za.dvt.myskilldevapp.features.forgotPassword.ForgotPasswordActivity
import co.za.dvt.myskilldevapp.features.login.fragments.UsersFragment
import co.za.dvt.myskilldevapp.features.registration.RegistrationActivity
import co.za.dvt.myskilldevapp.helpers.hideCurrentLoadingDialog
import co.za.dvt.myskilldevapp.helpers.showDialogFragment
import co.za.dvt.myskilldevapp.helpers.showLoadingDialog
import co.za.dvt.myskilldevapp.models.LoginModel
import co.za.dvt.myskilldevapp.models.UserModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseParentActivity()  {
    private lateinit var binding: ActivityLoginBinding
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var application = requireNotNull(this).application
        var viewModelFactory = LoginViewModelFactory(application)

        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        addObservers()

        txtSignUp.text = HtmlCompat.fromHtml(txtSignUp.text.toString(), 0)
        txtForgotYourUsername.text = HtmlCompat.fromHtml(txtForgotYourUsername.text.toString(),0)

        supportActionBar?.title = " HOST LOGIN"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_login_light)
    }

    private fun addObservers() {
        loginViewModel.showLoading.observe(this, Observer { toggleShowLoading(it) })
        loginViewModel.isValidDetails.observe(this, Observer { isValidSignInDetails(it) })
        loginViewModel.showContent.observe(this, Observer { toggleShowContent(it) })
        loginViewModel.showPreloadedUser.observe(this, Observer { toggleShowPreloadedUser(it) })
        loginViewModel.currentUser.observe(this, Observer { isUserSet(it) })
        loginViewModel.errorMessage.observe(this, Observer { onErrorMessageSet(it) })
    }

    private fun onErrorMessageSet(errorMessage: String) {
        clErrorContainer.visibility = View.VISIBLE
        clErrorContainer.blinkView(0.6f, 1.0f, 500, 2, Animation.ABSOLUTE, 0)
    }

    private fun onLoginResponse(loginModel: LoginModel){
        loginViewModel.onSigninCalled(loginModel)
    }

    private fun isUserSet(user: UserModel){
        var payload = Bundle()
        payload.putParcelable(USER, user)
        goToActivityWithPayload(DashboardActivity::class.java, payload, FADE_IN_ACTIVITY)
        finish()
    }

    fun onLoginButtonClicked(view: View){
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(txtPassword.windowToken, 0);
        loginViewModel.checkAndSignIn()
    }

    fun isValidSignInDetails(isValidDetails: Boolean){
        loginViewModel.signIn().observe(this, Observer { onLoginResponse(it) })
    }

    fun onForgotPassUserClicked(view: View){
        view.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
            goToActivityWithNoPayload(ForgotPasswordActivity::class.java, SLIDE_IN_ACTIVITY)
        }, {})
    }

    fun onRegisterClicked(view: View){
        view.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
            goToActivityWithNoPayload(RegistrationActivity::class.java, TRAIL_TO)
            finish()
        }, {})
    }

    private fun toggleShowLoading(isBusy: Boolean) {
        showLoadingDialog(loginViewModel.busyMessage, this)
    }

    private fun toggleShowContent(showContent: Boolean) {
        hideCurrentLoadingDialog(this)
    }

    private fun toggleShowPreloadedUser(showPreloadedUser: Boolean) {
        showSelectedUserLogin()
    }

    private fun showManualLogin() {
        loginViewModel.setManualMode()
        txMobile.visibility = View.VISIBLE
    }

    private fun showSelectedUserLogin() {
        txMobile.visibility = View.GONE
    }

    private fun showPreviousUserList() {
        var usersFragment = UsersFragment.newInstance()
        usersFragment.isCancelable = true
        showDialogFragment(
            getString(R.string.select_user),
            R.layout.fragment_users,
            usersFragment,
            this
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_manual_login -> showManualLogin()
            R.id.action_select_user -> showPreviousUserList()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true)
            return super.onKeyDown(keyCode, event)
        }
        return true
    }

}