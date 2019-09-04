package co.za.dvt.myskilldevapp.extensions

import android.view.View
import android.view.animation.AlphaAnimation

fun View.blinkView(fromAlpha: Float, toAlpha: Float, duration: Long, startOffset: Long, repeatMode: Int, repeatCount: Int){
    val anim = AlphaAnimation(fromAlpha, toAlpha)
    anim.duration = duration
    anim.startOffset = startOffset
    anim.repeatMode = repeatMode
    anim.repeatCount = repeatCount
    this.startAnimation(anim)
}