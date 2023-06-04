package com.briefing.brifieng6.ui.login.useralert
import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.briefing.test.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class CustomSnackbar private constructor(
    parent: ViewGroup,
    content: View,
    contentViewCallback: ContentViewCallback
) : BaseTransientBottomBar<CustomSnackbar>(parent, content, contentViewCallback) {

    companion object {
        @SuppressLint("ResourceAsColor")
        fun make(parent: ViewGroup, duration: Int): CustomSnackbar {
            val inflater = LayoutInflater.from(parent.context)
            val content = inflater.inflate(R.layout.custom_snackbar, parent, false)
            val contentViewCallback = object : ContentViewCallback {
                override fun animateContentIn(delay: Int, duration: Int) {
                    // do nothing
                }
                override fun animateContentOut(delay: Int, duration: Int) {
                    // do nothing
                }
            }
            return CustomSnackbar(parent, content, contentViewCallback).setDuration(duration)
        }
    }

    fun setText(text: String): CustomSnackbar {
        val textView = view.findViewById<TextView>(R.id.custom_snackbar_text)
        textView.text = text
        return this
    }

    fun setIcon(iconResId: Int): CustomSnackbar {
        val iconView = view.findViewById<ImageView>(R.id.custom_snackbar_icon)
        iconView.setImageResource(iconResId)
        iconView.visibility = View.VISIBLE
        return this
    }

}