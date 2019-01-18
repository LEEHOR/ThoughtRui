package com.coahr.thoughtrui.mvp.view.mydata;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.LoginFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.EvenBus_LoginSuccess;
import com.coahr.thoughtrui.mvp.model.Bean.LoginBean;
import com.coahr.thoughtrui.mvp.presenter.LoginFragmentP;
import com.coahr.thoughtrui.mvp.view.ConstantsActivity;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.callback.SaveCallback;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 21:38
 */
public class LoginFragment extends BaseFragment<LoginFragmentC.Presenter> implements LoginFragmentC.View {

    @Inject
    LoginFragmentP p;
    @BindView(R.id.user_account)
    EditText user_account;
    @BindView(R.id.user_password)
    EditText user_password;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    private int from; //从哪个页面来
    private int type; //类型
    /**
     *
     * @param type
     *
     * @param from
     *      来的页面
     * @return
     */
    public static LoginFragment newInstance(int type,int from) {
        LoginFragment loginFragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("from",from);
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    @Override
    public LoginFragmentC.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(user_account.getText()) || user_account.getText().toString().length()<4 ){
                    ToastUtils.showLong("请输入4位及以上字符的账户");
                    return;
                }
                if (TextUtils.isEmpty(user_password.getText()) || user_password.getText().toString().length()<4){
                    ToastUtils.showLong("请输入4位及以上字符的密码");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("username", user_account.getText().toString());
                map.put("password",user_password.getText().toString());
                p.Login(map);
            }
        });
   /*     user_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((editable !=null && !editable.toString().equals("") && editable.toString().length()>=3) && !TextUtils.isEmpty(user_password.getText()) && !user_password.getText().toString().equals("") && user_password.getText().toString().length()>=6){
                    loginBtn.setEnabled(true);
                    loginBtn.setBackgroundResource(R.color.colorAccent);
                } else {
                    loginBtn.setEnabled(false);
                    loginBtn.setBackgroundResource(R.drawable.bg_loginbtn);
                    ToastUtils.showLong("请输入正确的账户");
                }
            }
        });*/

    /*    user_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((editable !=null && !editable.toString().equals("") && editable.toString().length()>=6) && !TextUtils.isEmpty(user_account.getText()) && !user_account.getText().toString().equals("") && user_account.getText().toString().length()>=3){
                    loginBtn.setEnabled(true);
                    loginBtn.setBackgroundResource(R.color.colorAccent);
                } else {
                    loginBtn.setEnabled(false);
                    loginBtn.setBackgroundResource(R.drawable.bg_loginbtn);
                    ToastUtils.showLong("请输入正确的密码");
                }
            }
        });*/
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
             from= getArguments().getInt("from");
        }
    }

    @Override
    public void onLoginSuccess(final LoginBean loginBean) {
        ToastUtils.showLong("登录成功");
        UsersDB usersDB=new UsersDB();
        usersDB.setUserName(loginBean.getData().getName());
        usersDB.setSessionId(loginBean.getData().getSessionId());
        usersDB.saveOrUpdate("sessionid=?",loginBean.getData().getSessionId());
        Constants.sessionId=loginBean.getData().getSessionId();
        Constants.user_name=loginBean.getData().getName();
        PreferenceUtils.setPrefString(_mActivity,Constants.sessionId_key,loginBean.getData().getSessionId());
        PreferenceUtils.setPrefString(_mActivity,Constants.user_key,loginBean.getData().getName());
        if (from == Constants.MainActivityCode){
            EventBus.getDefault().postSticky(new EvenBus_LoginSuccess(100));
        }
        _mActivity.onBackPressed();


    }

    @Override
    public void onLoginFailure(String failure) {
        ToastUtils.showLong(failure);
    }

//    @Override
//    public boolean onBackPressedSupport() {
//        if (type == Constants.MainActivityCode){
//            return true;
//        } else {
//            return super.onBackPressedSupport();
//        }
//    }
}
