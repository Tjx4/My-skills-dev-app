package co.za.dvt.myskilldevapp.features.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.features.fragments.BaseDialogFragment
import co.za.dvt.myskilldevapp.helpers.ActivityTransitions
import co.za.dvt.myskilldevapp.helpers.showShortToast

abstract class BaseActivity : AppCompatActivity(), ActivityTransitions {
    var activeDialogFragment: BaseDialogFragment? = null
    var isNewActivity: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTransitions(this)
        isNewActivity = true
        supportActionBar?.elevation = 0f
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_help -> { showShortToast("Help...", this) }
        }
        return super.onOptionsItemSelected(item)
    }
}