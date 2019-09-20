package co.za.dvt.myskilldevapp.helpers

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun loadImageFromInternet(context: Context, url: String, imageView: ImageView, placeHolderPic: Int) {
    Glide.with(context).load(url).placeholder(placeHolderPic).fitCenter().into(imageView)
}

