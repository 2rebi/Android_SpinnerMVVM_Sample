package io.github.rebirthlee.spinnermvvm;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;

public class BindingAdapterHelper {
    @BindingAdapter({"imgUrl"})
    public static void loadProfileImage(ImageView view, String url) {
        GlideApp.with(view)
                .load(url)
                .circleCrop()
                .into(view);
    }
}
