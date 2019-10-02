package co.za.dvt.myskilldevapp.extensions

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.constants.ACTIVITY_TRANSITION
import co.za.dvt.myskilldevapp.constants.PAYLOAD_KEY
import co.za.dvt.myskilldevapp.models.Transition

fun AppCompatActivity.goToActivityWithNoPayload(activity: Class<*>, transitionAnimation: Transition) {
    goToActivity(activity, transitionAnimation, null)
}

private fun AppCompatActivity.goToActivity(activity: Class<*>, transitionAnimation: Transition, payload: Bundle?) {
    val intent = Intent(this, activity)

    val fullPayload = payload ?: Bundle()
    fullPayload.putIntArray(ACTIVITY_TRANSITION, intArrayOf(transitionAnimation.inAnimation, transitionAnimation.outAnimation))

    intent.putExtra(PAYLOAD_KEY, fullPayload)
    startActivity(intent)
}

private fun getTransitionAnimation(inAnimation: Int, outAnimation: Int): Transition {
    val transitionProvider = Transition()
    transitionProvider.inAnimation = inAnimation
    transitionProvider.outAnimation = outAnimation
    return transitionProvider
}

val SLIDE_IN_ACTIVITY = getTransitionAnimation(R.anim.slide_right, R.anim.no_transition)
val slideOutActivity =  getTransitionAnimation(R.anim.no_transition, R.anim.slide_left)