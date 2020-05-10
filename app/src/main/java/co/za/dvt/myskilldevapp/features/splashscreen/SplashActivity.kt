package co.za.dvt.myskilldevapp.features.splashscreen

import android.os.Bundle
import android.os.Handler
import co.za.dvt.myskilldevapp.extensions.FADE_IN_ACTIVITY
import co.za.dvt.myskilldevapp.extensions.goToActivityWithNoPayload
import co.za.dvt.myskilldevapp.features.activities.BaseActivity
import co.za.dvt.myskilldevapp.features.dashboard.DashboardActivity
import co.za.dvt.myskilldevapp.features.students.StudentsActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //For demo purposes delayed transition
        Handler().postDelayed({
            goToActivityWithNoPayload(StudentsActivity::class.java, FADE_IN_ACTIVITY)
            finish()
        }, 1000)
    }
}