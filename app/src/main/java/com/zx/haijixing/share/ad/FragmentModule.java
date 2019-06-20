package com.zx.haijixing.share.ad;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }
    @PerFragment
    @Provides
    @ContextLife("Activity")
    public Context provideActivityContext(){
        return mFragment.getActivity();
    }
    @Provides
    @PerFragment
    public Activity provideActivity(){
        return mFragment.getActivity();
    }
    @Provides
    @PerFragment
    public Fragment provideFragment(){
        return mFragment;
    }
}
