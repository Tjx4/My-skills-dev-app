package co.za.dvt.myskilldevapp.features.splashscreen

import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.extensions.FADE_IN_ACTIVITY
import co.za.dvt.myskilldevapp.extensions.goToActivityWithNoPayload
import co.za.dvt.myskilldevapp.features.activities.BaseActivity
import co.za.dvt.myskilldevapp.features.dashboard.DashboardActivity
import co.za.dvt.myskilldevapp.features.login.LoginActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }

//For demo purposes delayed transition
Handler().postDelayed({
    goToActivityWithNoPayload(LoginActivity::class.java, FADE_IN_ACTIVITY)
    finish()
}, 1000)
    }
}