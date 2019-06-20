package com.zx.haijixing.share.ad;

import android.content.Context;

import com.zx.haijixing.HaiApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private HaiApplication application;

    public ApplicationModule(HaiApplication application) {
        this.application = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext(){
        return application.getApplicationContext();
    }
}
