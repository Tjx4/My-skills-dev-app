package co.za.dvt.myskilldevapp.features.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.za.dvt.myskilldevapp.features.fragments.BaseDialogFragment
import co.za.dvt.myskilldevapp.helpers.ActivityTransitions

abstract class BaseActivity : AppCompatActivity(), ActivityTransitions {
    var activeDialogFragment: BaseDialogFragment? = null
    var isNewActivity: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTransitions(this)
        isNewActivity = true
        supportActionBar?.elevation = 0f
    }
}