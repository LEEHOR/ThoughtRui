package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.MyFragment_C;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/11
 * 描述：
 */
public class MyFragment_M extends BaseModel<MyFragment_C.Presenter> implements MyFragment_C.Model {
   @Inject
    public MyFragment_M() {
       super();
    }
}
