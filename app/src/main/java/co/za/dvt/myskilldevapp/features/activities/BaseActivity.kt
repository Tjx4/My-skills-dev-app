package co.za.dvt.myskilldevapp.features.activities

import androidx.appcompat.app.AppCompatActivity
import co.za.dvt.myskilldevapp.features.fragments.BaseDialogFragment

abstract class BaseActivity : AppCompatActivity() {
    var activeDialogFragment: BaseDialogFragment? = null
}