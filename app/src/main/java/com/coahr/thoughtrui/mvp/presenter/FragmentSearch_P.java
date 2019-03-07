package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.FragmentSearch_c;
import com.coahr.thoughtrui.mvp.model.Bean.SearchBeans;
import com.coahr.thoughtrui.mvp.model.FragmentSearch_M;
import com.coahr.thoughtrui.mvp.view.search.SearchFragment;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:50
 */
public class FragmentSearch_P extends BasePresenter<FragmentSearch_c.View,FragmentSearch_c.Model> implements FragmentSearch_c.Presenter
{
    @Inject
    public FragmentSearch_P(SearchFragment mview, FragmentSearch_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void getSearch(Map<String, Object> map) {
        if (mModle != null) {
            mModle.getSearch(map);
        }
    }

    @Override
    public void getSearchSuccess(SearchBeans searchBeans) {
        if (getView() != null) {
            getView().getSearchSuccess(searchBeans);
        }
    }

    @Override
    public void getSearchFailure(String fail) {
        if (getView() != null) {
            getView().getSearchFailure(fail);
        }
    }
}
