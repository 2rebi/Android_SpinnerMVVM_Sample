package io.github.rebirthlee.spinnermvvm.java;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;

import io.github.rebirthlee.spinnermvvm.java.GlideApp;

public class BindingAdapterHelper {
    @BindingAdapter({"imgUrl"})
    public static void loadProfileImage(ImageView view, String url) {
        GlideApp.with(view)
                .load(url)
                .circleCrop()
                .into(view);
    }
}
