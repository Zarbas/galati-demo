package com.demo.galatidemo.binding;

import android.app.Activity;
import android.databinding.DataBindingComponent;

/**
 * A Data Binding Component implementation for fragments.
 */
public class ActivityDataBindingComponent implements DataBindingComponent {
    private final ActivityBindingAdapters adapter;

    public ActivityDataBindingComponent(Activity activity) {
        this.adapter = new ActivityBindingAdapters(activity);
    }

    @Override
    public ActivityBindingAdapters getActivityBindingAdapters() {
        return adapter;
    }
}
