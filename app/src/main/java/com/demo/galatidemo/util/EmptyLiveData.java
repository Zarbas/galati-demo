package com.demo.galatidemo.util;

import android.arch.lifecycle.LiveData;

/**
 * An empty LiveData class with a null value. Used to initialize LiveData when real data
 * isn't fetched yet.
 */
public class EmptyLiveData extends LiveData {

    private EmptyLiveData() {
        postValue(null);
    }

    public static <T> LiveData<T> create() {
        return new EmptyLiveData();
    }
}
