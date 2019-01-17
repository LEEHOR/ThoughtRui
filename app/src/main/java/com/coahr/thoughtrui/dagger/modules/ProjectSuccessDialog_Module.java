package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.widgets.AltDialog.DialogFragmentAudioPlay;
import com.coahr.thoughtrui.widgets.AltDialog.ProjectSuccessDialog;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 18:11
 */
@Module
public class ProjectSuccessDialog_Module {
    @Provides
    public  String provideName() {
        return ProjectSuccessDialog.class.getName();
    }
}
