package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.widgets.AltDialog.Login_DialogFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 22:50
 */
@Module
public class Login_DialogFragmentModule {
    @Provides
    public  String provideName() {
        return Login_DialogFragment.class.getName();
    }
}
