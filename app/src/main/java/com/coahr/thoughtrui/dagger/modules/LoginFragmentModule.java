package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.MainActivity;
import com.coahr.thoughtrui.mvp.view.mydata.LoginFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 22:50
 */
@Module
public class LoginFragmentModule {
    @Provides
    public  String provideName() {
        return LoginFragment.class.getName();
    }
}
