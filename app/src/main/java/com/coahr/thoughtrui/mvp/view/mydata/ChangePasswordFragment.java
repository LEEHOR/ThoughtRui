package com.coahr.thoughtrui.mvp.view.mydata;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.KeyBoardUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.ChangePassWord_C;
import com.coahr.thoughtrui.mvp.model.Bean.ChangePassWord;
import com.coahr.thoughtrui.mvp.presenter.ChangePassWord_P;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/11
 * 描述：
 */
public class ChangePasswordFragment extends BaseFragment<ChangePassWord_C.Presenter> implements ChangePassWord_C.View {

    @Inject
    ChangePassWord_P p;
    @BindView(R.id.change_title)
    MyTittleBar change_title;
    @BindView(R.id.ed_old_pass)
    EditText ed_old_pass;
    @BindView(R.id.ed_new_pass1)
    EditText ed_new_pass1;
    @BindView(R.id.ed_new_pass2)
    EditText ed_new_pass2;
    @Override
    public ChangePassWord_C.Presenter getPresenter() {
        return p;
    }

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_change_password;
    }

    @Override
    public void initView() {
        change_title.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
        change_title.getRightText().setVisibility(View.VISIBLE);
        change_title.getRightText().setText("完成");
        change_title.getRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ed_old_pass.getText()) || ed_old_pass.getText().toString().equals("")){
                    ToastUtils.showLong("请输入正确的原始密码");
                    return;
                }
                if (TextUtils.isEmpty(ed_new_pass1.getText()) || ed_new_pass1.getText().toString().equals("") || ed_new_pass1.getText().toString().length()<4){
                    ToastUtils.showLong("请输入大于4位字符的新密码");
                    return;
                }
                if (TextUtils.isEmpty(ed_new_pass2.getText()) || !ed_new_pass1.getText().toString().equals(ed_new_pass1.getText().toString())){
                    ToastUtils.showLong("两次密码不相同");
                    return;
                }
                changePass();
            }
        });
    }

    @Override
    public void initData() {
        ed_old_pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setFocusable(1,ed_old_pass,ed_new_pass1,ed_new_pass2);
                return true;
            }
        });
        ed_new_pass1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setFocusable(1,ed_new_pass1,ed_new_pass2,ed_old_pass);
                return true;
            }
        });
        ed_new_pass2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setFocusable(1, ed_new_pass2, ed_new_pass1, ed_old_pass);
                return true;
            }
        });


    }

    @Override
    public void getChangePassSuccess(ChangePassWord changePassWord) {
                ToastUtils.showLong(changePassWord.getMsg());
        _mActivity.onBackPressed();
    }

    @Override
    public void getChangePassFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    private void setFocusable(int type,EditText e1,EditText e2,EditText e3){
      if (type==1){
          e1.setFocusable(true);
          e1.setFocusableInTouchMode(true);
      } else {
          e2.setFocusable(true);
          e2.setFocusableInTouchMode(true);
          e3.setFocusable(true);
          e3.setFocusableInTouchMode(true);
      }
    }
    private void changePass(){
        Map map=new HashMap();
        map.put("sessionId",Constants.sessionId);
        map.put("oldPassword",ed_old_pass.getText().toString());
        map.put("newPassword",ed_new_pass2.getText().toString());
        p.getChangePass(map);
    }
}
