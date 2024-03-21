package com.app.contentproviderdemo.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat

class CustomBindingAdapter {


    companion object {
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

        @JvmStatic
        @BindingAdapter(value = ["setCallDuration"], requireAll = false)
        fun setCallDuration(textView: TextView?, duration: Long) {

            if (textView != null) {
                textView.setText("$duration s")
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["setCallDate"], requireAll = false)
        fun setCallDate(textView: TextView?, date: Long) {

            if (textView != null) {
                val formattedDate: String = sdf.format(date)

                textView.setText(formattedDate)
            }
        }
    }
}