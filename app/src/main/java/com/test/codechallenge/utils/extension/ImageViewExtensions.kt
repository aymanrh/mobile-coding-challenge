package com.test.codechallenge.utils.extension

import android.widget.ImageView
import android.widget.TextView
import com.test.codechallenge.GlideApp
import com.test.codechallenge.R


fun ImageView.load(url: String) {
    GlideApp.with(context)
        .load(url)
        .placeholder(R.drawable.ic_launcher_background)
        .into(this)
}

fun TextView.toK(number: Int?) {
    if (number != null) {
        this.text = when {
            Math.abs(number.div(1000000)) > 1 -> (number.div(1000000)).toString() + "m"
            Math.abs(number.div(1000)) > 1 -> (number.div(1000)).toString() + "k"
            else -> number.toString()
        }
    } else {
        this.text = "0"
    }
}