package com.yabasoft.henripotier.data.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Created by Fayssel Yabahddou on 4/15/22.
 */

@BindingAdapter(value = ["imageUrl"])
fun ImageView.loadImage(url: String) {
    Glide.with(this.context).load(url).into(this)
}