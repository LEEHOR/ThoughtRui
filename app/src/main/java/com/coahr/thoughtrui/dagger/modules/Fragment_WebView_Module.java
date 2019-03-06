package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.WebView.Fragment_WebView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 18:11
 */
@Module
public class Fragment_WebView_Module {
    @Provides
    public  String provideName() {
        return Fragment_WebView.class.getName();
    }
}
