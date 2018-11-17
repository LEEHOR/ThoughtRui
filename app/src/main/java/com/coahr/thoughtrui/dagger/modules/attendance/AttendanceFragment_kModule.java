package com.coahr.thoughtrui.dagger.modules.attendance;

import com.coahr.thoughtrui.mvp.view.attence.AttendanceFragment_k;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 13:20
 */
@Module
public class AttendanceFragment_kModule {
    @Provides
    public  String provideName() {
        return AttendanceFragment_k.class.getName();
    }
}
