package co.za.dvt.myskilldevapp.extensions

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation

fun View.blinkView(fromAlpha: Float, toAlpha: Float, duration: Long, startOffset: Long, repeatMode: Int, repeatCount: Int){
    val anim = AlphaAnimation(fromAlpha, toAlpha)
    anim.duration = duration
    anim.startOffset = startOffset
    anim.repeatMode = repeatMode
    anim.repeatCount = repeatCount
    this.startAnimation(anim)
}

fun View.rotateView(fromDegrees: Float, toDegrees: Float, pivotXValue: Float, pivotYValue: Float, duration: Long, startOffset: Long, repeatMode: Int, repeatCount: Int, onAnimationFinished: () -> Unit = {}, onAnimationStarted: () -> Unit = {} ,onAnimationRepeated: () -> Unit = {} ){

    val anim = RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, pivotXValue, Animation.RELATIVE_TO_SELF, pivotYValue)
    anim.interpolator = LinearInterpolator()
    anim.duration = duration
    anim.startOffset = startOffset
    anim.repeatMode = repeatMode
    anim.repeatCount = repeatCount

    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            onAnimationStarted()
        }

        override fun onAnimationEnd(animation: Animation) {
            onAnimationFinished()
        }

        override fun onAnimationRepeat(animation: Animation) {
            onAnimationRepeated()
        }
    })
    this.startAnimation(anim)
}