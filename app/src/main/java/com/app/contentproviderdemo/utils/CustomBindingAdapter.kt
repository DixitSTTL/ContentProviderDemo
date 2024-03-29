package com.app.contentproviderdemo.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

class CustomBindingAdapter {


    companion object {
        private val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US)
        private val decimalFormat = DecimalFormat("##.##")

        @JvmStatic
        @BindingAdapter(value = ["setCallDuration"], requireAll = false)
        fun setCallDuration(textView: TextView?, duration: Long) {
            textView?.text = "$duration s"
        }

        @JvmStatic
        @BindingAdapter(value = ["setCallDate"], requireAll = false)
        fun setCallDate(textView: TextView?, date: Long) {

            if (textView != null) {
                val formattedDate: String = sdf.format(date)

                textView.text = formattedDate
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["setImagesFromData"], requireAll = false)
        fun setImagesFromData(imageView: ImageView?, data: String) {
            Glide.with(imageView!!.context)
                .load(data)
                .into(imageView)

        }

        @JvmStatic
        @BindingAdapter(value = ["setImageSize"], requireAll = false)
        fun setImageSize(textView: TextView?, data: Long) {
            if (textView != null) {
                val size = sizeConvertor(data)
                textView.text = size
            }

        }

        private fun sizeConvertor(data: Long): String {

            return decimalFormat.format(data.toDouble() / 1000000) + " Mb"

        }
    }
}