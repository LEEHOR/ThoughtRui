package com.coahr.thoughtrui.mvp.view.projectAnnex;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.viewpager.widget.ViewPager;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.view.projectAnnex.adapter.AnnexViewPagerAdapter;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/16
 * 描述：项目附件ViewPager
 */
public class FragmentAnnexViewPager extends BaseFragment {
    @BindView(R.id.annex_tittle)
    MyTittleBar annex_tittle;
    @BindView(R.id.annex_tab)
    TabLayout annex_tab;
    @BindView(R.id.annex_viewpager)
    ViewPager annex_viewpager;
    @BindView(R.id.annex_RG)
    RadioGroup annexRG;
    @BindView(R.id.rbt_1)
    RadioButton rbt1;
    @BindView(R.id.rbt_2)
    RadioButton rbt2;
    @BindView(R.id.rbt_3)
    RadioButton rbt3;
    private AnnexViewPagerAdapter annexViewPagerAdapter;

    public static FragmentAnnexViewPager newInstance() {
        return new FragmentAnnexViewPager();
    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_annex_viewpager;
    }

    @Override
    public void initView() {
        annexRG.setOnCheckedChangeListener(new RadioGroupListener());
        annex_tittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
        annexViewPagerAdapter = new AnnexViewPagerAdapter(getChildFragmentManager(), Constants.ht_ProjectId);
        annex_viewpager.setAdapter(annexViewPagerAdapter);
        annex_viewpager.setCurrentItem(0);
        annex_viewpager.addOnPageChangeListener(new ViewPageListener());
        //  annex_tab.setupWithViewPager(annex_viewpager);

    }

    @Override
    public void initData() {

    }

    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                case R.id.rbt_1:
                    annex_viewpager.setCurrentItem(0);
                    break;
                case R.id.rbt_2:
                    annex_viewpager.setCurrentItem(1);
                    break;
                case R.id.rbt_3:
                    annex_viewpager.setCurrentItem(2);
                    break;
            }
        }
    }

    class ViewPageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    rbt1.setChecked(true);
                    break;
                case 1:
                    rbt2.setChecked(true);
                    break;
                case 2:
                    rbt3.setChecked(true);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
