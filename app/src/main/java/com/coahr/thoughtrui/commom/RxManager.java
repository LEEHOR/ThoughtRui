package com.coahr.thoughtrui.commom;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 11:24
 */
public class RxManager {
    /*管理Observables 和 Subscribers订阅*/
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * 单纯的Observables 和 Subscribers管理
     *
     * @param m
     */
    public void add(Disposable m) {
        /*订阅管理*/
        mCompositeDisposable.add(m);
    }

    /**
     * 单个model生命周期结束，取消订阅和所有rxbus观察
     */
    public void clear() {
        if (mCompositeDisposable!=null) {
            mCompositeDisposable.clear();// 取消所有订阅
        }
    }
}
