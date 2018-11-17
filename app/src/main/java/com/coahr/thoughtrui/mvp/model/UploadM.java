package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.UploadC;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:09
 */
public class UploadM extends BaseModel<UploadC.Presenter> implements UploadC.Model {
    @Inject
    public UploadM() {
        super();
    }

    @Override
    public void startLocation() {

    }

    @Override
    public void getHomeData(Map map) {

    }
}
