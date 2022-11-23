package dev.sdckp.githubuserbrowser.common

import android.view.View
import android.widget.TextView

fun TextView.textOrGone(text: String?) {
    if (text.isNullOrBlank()) {
        visibility = View.GONE
        return
    }
    visibility = View.VISIBLE
    this.text = text
}