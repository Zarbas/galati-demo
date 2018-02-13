package com.demo.galatidemo.binding;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

/**
 * Binding the adapters used with the activities
 */
public class ActivityBindingAdapters {
    private static final String baseUrl = "https://api.adorable.io/avatars/285/%s.png";

    final Activity activity;

    @Inject
    public ActivityBindingAdapters(Activity activity) {
        this.activity = activity;
    }

    /**
     * create a new userEmail tag used to fetch the images with Glide
     * in layout xml files I can simple use app:userEmail="@{user.email}" to retrieve the image
     * from the net asynchronously
     */
    @BindingAdapter("userEmail")
    public void bindImage(ImageView imageView, String userEmail) {
        Glide.with(activity).load(String.format(baseUrl, userEmail)).into(imageView);
    }
}
