package com.coahr.thoughtrui.dagger.modules.attendance;

import com.coahr.thoughtrui.mvp.view.attence.AttendanceFragment_h;


import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 13:20
 */
@Module
public class AttendanceFragment_hModule {
    @Provides
    public  String provideName() {
        return AttendanceFragment_h.class.getName();
    }
}
