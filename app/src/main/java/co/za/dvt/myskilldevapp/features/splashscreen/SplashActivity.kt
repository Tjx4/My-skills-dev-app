package co.za.dvt.myskilldevapp.features.splashscreen

import android.os.Bundle
import android.os.Handler
import co.za.dvt.myskilldevapp.extensions.FADE_IN_ACTIVITY
import co.za.dvt.myskilldevapp.extensions.goToActivityWithNoPayload
import co.za.dvt.myskilldevapp.features.activities.BaseActivity
import co.za.dvt.myskilldevapp.features.characters.StudentsActivity
import co.za.dvt.myskilldevapp.features.houses.HousesActivity

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