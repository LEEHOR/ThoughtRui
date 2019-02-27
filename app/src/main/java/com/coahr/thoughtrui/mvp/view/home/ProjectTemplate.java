package com.coahr.thoughtrui.mvp.view.home;

import android.graphics.Color;
import android.view.View;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.ProjectTemplate_c;
import com.coahr.thoughtrui.mvp.presenter.ProjectTemplate_P;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.gyf.barlibrary.ImmersionBar;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：项目模板
 */
public class ProjectTemplate extends BaseFragment<ProjectTemplate_c.Presenter> implements ProjectTemplate_c.View {

    @Inject
    ProjectTemplate_P p;
    @BindView(R.id.template_title)
    MyTittleBar template_title;
    public static ProjectTemplate newInstance() {
        return new ProjectTemplate();
    }
    @Override
    public ProjectTemplate_c.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_projecttemplate;
    }

    @Override
    public void initView() {
        //手机顶部状态栏颜色适配
        ImmersionBar.with(this)
                .statusBarColor(R.color.material_blue_350)
                .statusBarDarkFont(false)
                .init();
        template_title.getRoot().setBackgroundColor(Color.rgb(58,128,255));
        template_title.getTvTittle().setTextColor(Color.rgb(255,255,255));
        template_title.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void getProjectTemplatesSuccess() {

    }

    @Override
    public void getProjectTemplateFailure(String fail) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
