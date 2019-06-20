package com.zx.haijixing.share.ad;

import android.app.Activity;
import android.content.Context;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getAcitivtyContext();
    @ContextLife("Application")
    Context getApplicationContext();

    Activity getAcitivty();
   // void inject(UndoneFragment fragment);
    //void inject(DoneFragment fragment);
}
