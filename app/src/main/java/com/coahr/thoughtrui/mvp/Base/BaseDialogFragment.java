package com.coahr.thoughtrui.mvp.Base;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.KeyBoardUtils;
import com.coahr.thoughtrui.Utils.ScreenUtils;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

/**
 * Author： hengzwd on 2018/7/18.
 * Email：hengzwdhengzwd@qq.com
 */
public abstract class BaseDialogFragment<P extends BaseContract.Presenter> extends AppCompatDialogFragment implements BaseContract.View {

    private Unbinder unbinder;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    protected int animate_style = R.style.top_in_out_animation;
    protected View addFootView;
    private View view;

    public abstract P getPresenter();

    public abstract int bindLayout();

    public abstract void initView();

    public abstract void initData();

    public abstract void initAnimate();

    public abstract void iniWidow(Window window);

    private Dialog dialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAnimate();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this); //一处声明，处处依赖注入
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setNavigationBarColor(getResources().getColor(R.color.colorWhite));
        }
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(bindLayout(), container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        unbinder = ButterKnife.bind(this, view);
        //由于沉浸式要留白 标题栏，在这里统一设置，支出statusbar的空间，之后每个fragment的头顶第一个子view，都要
        //以一个viewgroup包含要显示tittle的子view形式进行布局，则此代码正确有效

        UpdateUI(view.getRootView());
        addFootView = inflater.inflate(R.layout.recyclerview_item_foot, container, false);
        initView();
        initData();

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
      /*  Window window = dialog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(R.drawable.bg_fff_background);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            window.setWindowAnimations(animate_style);
        }*/
       iniWidow(dialog.getWindow());
        return dialog;
    }

    public void showError(Throwable t) {

    }

    @Override
    public void showLoading() {

        //创建Dialog并传递style文件
        if (dialog == null) {
//            dialog = new AlertDialog.Builder(getActivity(),R.style.dialog_loading).create();
//            dialog.show();
//            dialog.setContentView(R.layout.dialog_loading_layout);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable());//解决5.0以上阴影问题
            dialog = new Dialog(getActivity(), R.style.dialog_loading);
            dialog.setContentView(R.layout.dialog_loading_layout);
        }
        // 设置它的ContentView
        if (!dialog.isShowing()) {
            dialog.show();//显示dialog
        }
    }

    @Override
    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
//        EventBus.getDefault().unregister(this);
        if (getPresenter() != null) {
            getPresenter().detachView();
        }
    }


    public void UpdateUI(View view) {//解决所有页面   touch所有edittext以外view，自动隐藏键盘  通过decorview可以获取所有子view，循环判断设置touch事件
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    KeyBoardUtils.hideKeybord(arg0, getActivity());
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                UpdateUI(innerView);
            }
        }
    }

    /**
     * 设置头部下沉
     */
    public  void setToolBarPadding(){
    View tittleView = ((ViewGroup) view.getRootView()).getChildAt(0);
    tittleView.setPadding(tittleView.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tittleView.getPaddingRight(), tittleView.getPaddingBottom());
}
}
