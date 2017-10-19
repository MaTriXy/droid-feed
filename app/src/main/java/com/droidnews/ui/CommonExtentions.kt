package com.droidnews.ui

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.droidnews.R

/**
 *  Commonly used extension functions.
 *
 * Created by Dogan Gulcan on 9/30/17.
 */

/**
 * Loads given image url into the ImageView via Glide.
 */
fun ImageView.loadUrl(url: String) {
    Glide.with(context)
            .load(url)
            .apply(RequestOptions()
                    .error(context.getDrawable(R.drawable.ic_broken_image_black_24dp)))
            .into(this)
}