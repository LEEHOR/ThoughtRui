package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.upload.UploadFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:15
 */
@Module
public class UploadFragmentModule {
    @Provides
    public  String provideName() {
        return UploadFragment.class.getName();
    }
}
