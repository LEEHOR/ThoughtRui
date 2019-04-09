package com.coahr.thoughtrui.mvp.view.projectAnnex.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.view.projectAnnex.FragmentAnnex;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/16
 * 描述：
 */
public class AnnexViewPagerAdapter extends FragmentPagerAdapter {
    private String[] title = {BaseApplication.mContext.getString(R.string.project_annex_all)
            , BaseApplication.mContext.getString(R.string.project_annex_imgs)
            , BaseApplication.mContext.getString(R.string.project_annex_audios)};
    private String projectId;

    public AnnexViewPagerAdapter(FragmentManager fm, String projectIds) {
        super(fm);
        this.projectId = projectIds;
    }

    @Override
    public Fragment getItem(int i) {
        return FragmentAnnex.newInstance(i, projectId);
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
