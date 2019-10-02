package co.za.dvt.myskilldevapp.helpers

import android.app.Activity
import android.util.Log
import co.za.dvt.myskilldevapp.constants.ACTIVITY_TRANSITION
import co.za.dvt.myskilldevapp.constants.PAYLOAD_KEY

interface ActivityTransitions {

    fun initTransitions(activity: Activity) {
        try {
            val activityTransition = activity.intent.getBundleExtra(PAYLOAD_KEY).getIntArray(
                ACTIVITY_TRANSITION
            )
            activity.overridePendingTransition(activityTransition!![0], activityTransition[1])
        } catch (e: Exception) {
          Log.e("TR", "$e")
        }
    }
}