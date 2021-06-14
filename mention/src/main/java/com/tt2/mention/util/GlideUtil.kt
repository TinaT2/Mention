package com.tt2.mention.util

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object GlideUtil {

    fun loadAvatarImage(filePath: String?, imageView: ImageView, @DrawableRes placeHolder: Int,context: Context) {
        Glide.with(context)
            .load(filePath)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(placeHolder)
            .into(imageView)
    }
}