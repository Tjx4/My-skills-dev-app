package co.za.dvt.myskilldevapp.features.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import co.za.dvt.myskilldevapp.extensions.SLIDE_IN_ACTIVITY
import co.za.dvt.myskilldevapp.extensions.goToActivityWithNoPayload
import co.za.dvt.myskilldevapp.features.dashboard.DashboardActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            goToActivityWithNoPayload(DashboardActivity::class.java, SLIDE_IN_ACTIVITY)
        }, 1000)
    }
}