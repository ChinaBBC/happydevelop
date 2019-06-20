package com.zx.haijixing.share.ad;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

@PerApp
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ContextLife("Application")
    Context getApplication();
}
