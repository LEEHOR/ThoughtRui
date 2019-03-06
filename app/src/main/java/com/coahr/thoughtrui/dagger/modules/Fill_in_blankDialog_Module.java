package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.widgets.AltDialog.DialogFragmentAudioPlay;
import com.coahr.thoughtrui.widgets.AltDialog.Fill_in_blankDialog;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 18:11
 */
@Module
public class Fill_in_blankDialog_Module {
    @Provides
    public  String provideName() {
        return Fill_in_blankDialog.class.getName();
    }
}
