package com.coahr.thoughtrui.mvp.Base;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 15:37
 */
public interface BaseContract {

    interface View {
        void showError(Throwable t);
        void showLoading();
        void dismissLoading();
    }

    interface Presenter {

        void showLoading();

        void dismissLoading();

        void showError(Throwable t);

        void detachView();

    }

    interface Model {

        void  AttachPresenter(BaseContract.Presenter presenter);
        void detachPresenter();
    }
}
