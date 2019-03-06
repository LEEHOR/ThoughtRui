package com.coahr.thoughtrui.widgets.AltDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.Utils.ScreenUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseDialogFragment;
import com.coahr.thoughtrui.mvp.constract.LoginFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.EvenBus_LoginSuccess;
import com.coahr.thoughtrui.mvp.model.Bean.LoginBean;
import com.coahr.thoughtrui.mvp.presenter.LoginFragmentP;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/24
 * 描述：
 */
public class Login_DialogFragment extends BaseDialogFragment<LoginFragmentC.Presenter> implements LoginFragmentC.View {
    @Inject
    LoginFragmentP p;
    @BindView(R.id.user_account)
    EditText user_account;
    @BindView(R.id.user_password)
    EditText user_password;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    private loginListener loginListener;
    private int pager_number;

    @Override
    public LoginFragmentC.Presenter getPresenter() {
        return p;
    }

    /**
     * 页码
     * @param pager_number
     * @return
     */
    public static Login_DialogFragment newInstance(int pager_number) {
        Login_DialogFragment login_dialogFragment = new Login_DialogFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("pager_number",pager_number);
        login_dialogFragment.setArguments(bundle);
        return login_dialogFragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {
        this.getDialog().setCancelable(false);
        this.getDialog().setCanceledOnTouchOutside(false);
        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

                    return true;
                }
                return false;
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(user_account.getText()) || user_account.getText().toString().length() < 4) {
                    ToastUtils.showLong("请输入4位及以上字符的账户");
                    return;
                }
                if (TextUtils.isEmpty(user_password.getText()) || user_password.getText().toString().length() < 4) {
                    ToastUtils.showLong("请输入4位及以上字符的密码");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("username", user_account.getText().toString());
                map.put("password", user_password.getText().toString());
                map.put("token",Constants.devicestoken);
                p.Login(map);
            }
        });
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            pager_number = getArguments().getInt("pager_number");
        }
        clearAccount();
    }

    @Override
    public void initAnimate() {

    }

    @Override
    public void iniWidow(Window window) {
        if (window != null) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(lp.MATCH_PARENT, lp.MATCH_PARENT);
            window.setWindowAnimations(R.style.Photo_See_Animation);
        }
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        ToastUtils.showLong("登录成功");
        List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", loginBean.getData().getSessionId());
        if (usersDBS != null && usersDBS.size() > 0) {

        } else {
            UsersDB usersDB = new UsersDB();
            usersDB.setUserName(loginBean.getData().getName());
            usersDB.setSessionId(loginBean.getData().getSessionId());
            usersDB.setType(loginBean.getData().getType());
            usersDB.save();
        }
        Constants.sessionId = loginBean.getData().getSessionId();
        Constants.user_name = loginBean.getData().getName();
        Constants.user_type=loginBean.getData().getType();
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.sessionId_key, loginBean.getData().getSessionId());
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.user_key, loginBean.getData().getName());
        PreferenceUtils.setPrefInt(BaseApplication.mContext,Constants.user_type_key,loginBean.getData().getType());
       /* if (pager_number== Constants.MyTabFragmentCode) {

        } else if (pager_number==Constants.MainActivityCode){
            if (loginListener != null) {
                loginListener.loginSuccess(this);
            }
        } else if (pager_number==Constants.fragment_myFragment){
            if (loginListener != null) {
                loginListener.loginSuccess(this);
            }
        } else {

        }*/
        if (loginListener != null) {
            loginListener.loginSuccess(this);
        }
    }

    @Override
    public void onLoginFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    public interface loginListener {
        void loginSuccess(AppCompatDialogFragment dialogFragment);
    }

    public void setLoginListener(loginListener loginListener) {
        this.loginListener = loginListener;
    }

    /**
     * 清楚当前账户信息
     */
    private void clearAccount(){
        Constants.wan_ka = null;
        Constants.zao_ka = null;
        Constants.DbProjectId = null;
        Constants.ht_ProjectId = null;
        Constants.user_name = "";
        Constants.sessionId = "";
        PreferenceUtils.remove(BaseApplication.mContext, Constants.user_key);
        PreferenceUtils.remove(BaseApplication.mContext, Constants.sessionId_key);
    }
}
