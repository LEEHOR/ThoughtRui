package com.coahr.thoughtrui.mvp.Base;

import android.support.annotation.UiThread;

import java.lang.ref.WeakReference;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 15:39
 */
public abstract class BasePresenter <V extends BaseContract.View,M extends BaseContract.Model> implements BaseContract.Presenter {
    //由于View被系统或者activity强引用，所以不担心随时被回收，当强应用指向null时，此引用随时被回收，避免内存泄漏
    public WeakReference<V> mView;
    //model被presenter单个持有引用，不适合用弱引用，会随时回收，影响app运行

    public M mModle;

    public BasePresenter(V mview,M mModel) {
        attachView(mview);
        this.mModle=mModel;
        mModel.AttachPresenter(this);
        //   KLog.e("BasePresenter",this.getClass().toString());
    }


    //所有错误回调都走这个方法传递到view层
    public void showError(Throwable t) {
        if (getView() != null) {
            getView().showError(t);
        }
    }
    @Override
    public void showLoading() {
        if (getView() != null) {
            getView().showLoading();
        }
    }

    @Override
    public void dismissLoading() {
        if (getView() != null) {
            getView().dismissLoading();
        }
    }

    public V getView() {
        return mView==null?null:mView.get();
    }


    public void attachView(V view) {
        mView = new WeakReference<V>(view);
    }

    @UiThread
    public boolean isViewAttached() {
        return mView != null&&mView.get()!=null;
    }

    @UiThread
    public void detachView() {
        if (getView() != null) {
            mView.clear();
            mView = null;
        }
        if (mModle != null) {
            mModle.detachPresenter();
        }
        mModle=null;
    }
}
