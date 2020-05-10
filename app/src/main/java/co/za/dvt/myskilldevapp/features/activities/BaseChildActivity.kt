package co.za.dvt.myskilldevapp.features.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import co.za.dvt.myskilldevapp.extensions.SLIDE_OUT_ACTIVITY

abstract class BaseChildActivity : BaseActivity() {

    protected var childActionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setChildActionbarActivityDependencies(actionBar : ActionBar?) {
        childActionBar = actionBar
        childActionBar?.setDisplayUseLogoEnabled(false)
        childActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        SLIDE_OUT_ACTIVITY
        overridePendingTransition(SLIDE_OUT_ACTIVITY.inAnimation, SLIDE_OUT_ACTIVITY.outAnimation)
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(SLIDE_OUT_ACTIVITY.inAnimation, SLIDE_OUT_ACTIVITY.outAnimation)
    }
}