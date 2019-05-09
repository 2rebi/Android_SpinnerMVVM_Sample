package io.github.rebirthlee.spinnermvvm.kotlin

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object BindingAdapterHelper {
    @JvmStatic
    @BindingAdapter("imgUrl")
    fun loadProfileImage(view: ImageView, url: String) {
        GlideApp.with(view)
                .load(url)
                .circleCrop()
                .into(view)
    }
}
