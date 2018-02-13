package com.demo.galatidemo.injection;

import com.demo.galatidemo.ui.DetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Module for DetailActivity injection
 */

@Module
public abstract class DetailActivityModule {
    @ContributesAndroidInjector
    abstract DetailActivity contributeDetailActivity();
}
