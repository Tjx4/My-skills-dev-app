package co.za.dvt.myskilldevapp.features.login

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.databinding.ActivityLoginBinding
import co.za.dvt.myskilldevapp.features.activities.BaseActivity
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.login.fragments.UsersFragment
import co.za.dvt.myskilldevapp.helpers.hideCurrentLoadingDialog
import co.za.dvt.myskilldevapp.helpers.showDialogFragment
import co.za.dvt.myskilldevapp.helpers.showLoadingDialog
import kotlinx.android.synthetic.main.activity_dashboard.clCParent
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity()  {

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

        supportActionBar?.title = "Sign in"
        supportActionBar?.setIcon(R.drawable.ic_pa_light)
    }

    private fun addObservers() {
        loginViewModel.showLoading.observe(this, Observer { toggleShow(it) })
        loginViewModel.showContent.observe(this, Observer { toggleShowContent(it) })
        loginViewModel.showPreloadedUser.observe(this, Observer { toggleShowPreloadedUser(it) })
        loginViewModel.previousUsers.observe(this, Observer { toggleShowUsersList(it) })
    }

    public fun onTestButtonClicked(view: View){
        loginViewModel.testFetchSomethingFromAPI()
    }

    private fun toggleShow(isBusy: Boolean) {
        clCParent.visibility = View.INVISIBLE
        showLoadingDialog(loginViewModel.busyMessage, this)
    }

    private fun toggleShowContent(showContent: Boolean) {
        hideCurrentLoadingDialog(this)
        clCParent.visibility = View.VISIBLE
    }

    private fun toggleShowUsersList(previousUsers: List<UsersTable>) {
        // Todo: Remove or use if users not loaded from frament
    }

    private fun toggleShowPreloadedUser(showPreloadedUser: Boolean) {
        showSelectedUserLogin()
    }

    private fun showManualLogin() {
        txtUsername.visibility = View.VISIBLE
        llUsername.visibility = View.GONE
    }

    private fun showSelectedUserLogin() {
        txtUsername.visibility = View.GONE
        llUsername.visibility = View.VISIBLE
    }

    private fun showPreviousUserList() {
        var usersFragment = UsersFragment.newInstance()
        usersFragment.isCancelable = true
        showDialogFragment(getString(R.string.stats_history), R.layout.fragment_users, usersFragment, this)
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
