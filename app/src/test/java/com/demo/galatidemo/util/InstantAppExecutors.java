package com.demo.galatidemo.util;

import android.support.annotation.NonNull;

import com.demo.galatidemo.AppExecutors;

import java.util.concurrent.Executor;

public class InstantAppExecutors extends AppExecutors {
    private static Executor instant = new Executor() {
        @Override
        public void execute(@NonNull Runnable runnable) {
            runnable.run();
        }
    };

    public InstantAppExecutors() {
        super(instant, instant, instant);
    }
}
