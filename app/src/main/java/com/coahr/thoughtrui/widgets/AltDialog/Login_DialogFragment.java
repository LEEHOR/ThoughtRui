package com.coahr.thoughtrui.widgets.AltDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseDialogFragment;
import com.coahr.thoughtrui.mvp.constract.LoginFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.LoginBean;
import com.coahr.thoughtrui.mvp.presenter.LoginFragmentP;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

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
    private String token;
    //随机数
    private static final char[] CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    private static final int DEFAULT_CODE_LENGTH = 15;

    @Override
    public LoginFragmentC.Presenter getPresenter() {
        return p;
    }

    /**
     * 页码
     *
     * @param pager_number
     * @return
     */
    public static Login_DialogFragment newInstance(int pager_number) {
        Login_DialogFragment login_dialogFragment = new Login_DialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pager_number", pager_number);
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
                if (TextUtils.isEmpty(user_account.getText().toString().trim()) /*|| user_account.getText().toString().trim().length() < 4*/) {
                    ToastUtils.showLong(getString(R.string.toast_1));
                    return;
                }
                if (TextUtils.isEmpty(user_password.getText().toString().trim()) || user_password.getText().toString().trim().length() < 6) {
                    ToastUtils.showLong(getString(R.string.toast_2));
                    return;
                }
                String prefString = PreferenceUtils.getPrefString(BaseApplication.mContext, Constants.devicestoken_key, "");
                KLog.e("测试代码", "prefString == " + prefString);
                if (prefString != null && !prefString.equals("")) {
                    token = prefString;
                } else {
                    StringBuffer sb = new StringBuffer();
                    Random random = new Random();
                    for (int i = 0; i < 15; i++) {
                        sb.append(CHARS[random.nextInt(CHARS.length)]);
                    }
                    token = sb.toString();
                }

                Map<String, Object> map = new HashMap<>();
                map.put("username", user_account.getText().toString());
                map.put("password", user_password.getText().toString());
                map.put("token", token);
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
        ToastUtils.showLong(getString(R.string.toast_5));
        List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", loginBean.getData().getSessionId());
        if (usersDBS != null && usersDBS.size() > 0) {
            usersDBS.get(0).setType(loginBean.getData().getType());
            usersDBS.get(0).update(usersDBS.get(0).getId());
        } else {
            UsersDB usersDB = new UsersDB();
            usersDB.setUserName(loginBean.getData().getName());
            usersDB.setSessionId(loginBean.getData().getSessionId());
            usersDB.setType(loginBean.getData().getType());
            usersDB.save();
        }
        Constants.sessionId = loginBean.getData().getSessionId();
        Constants.user_name = loginBean.getData().getName();
        Constants.user_type = loginBean.getData().getType();
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.sessionId_key, loginBean.getData().getSessionId());
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.user_key, loginBean.getData().getName());
        PreferenceUtils.setPrefInt(BaseApplication.mContext, Constants.user_type_key, loginBean.getData().getType());
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
    private void clearAccount() {
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
