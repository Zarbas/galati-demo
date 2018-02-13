package com.demo.galatidemo.injection;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.demo.galatidemo.DemoApp;

import dagger.android.AndroidInjection;

/**
 * Init Dagger App Component and inject the required dependencies in every Activity created
 */
public class AppInjector {

    private AppInjector() {

    }

    public static void init(DemoApp demoApp) {
        DaggerAppComponent.builder().application(demoApp).build().inject(demoApp);

        demoApp.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        AndroidInjection.inject(activity);
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {

                    }

                    @Override
                    public void onActivityResumed(Activity activity) {

                    }

                    @Override
                    public void onActivityPaused(Activity activity) {

                    }

                    @Override
                    public void onActivityStopped(Activity activity) {

                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {

                    }
                });
    }
}
