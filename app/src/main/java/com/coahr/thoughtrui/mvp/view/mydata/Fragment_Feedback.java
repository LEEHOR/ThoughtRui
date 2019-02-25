package com.coahr.thoughtrui.mvp.view.mydata;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.Feedback_Fragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.FeedBack;
import com.coahr.thoughtrui.mvp.presenter.Feedback_Fragment_P;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/22
 * 描述：意见反馈
 */
public class Fragment_Feedback extends BaseFragment<Feedback_Fragment_C.Presenter> implements Feedback_Fragment_C.View, View.OnClickListener {
    @Inject
    Feedback_Fragment_P p;
    @BindView(R.id.ck_btn_1)
    CheckBox ck_btn_1;
    @BindView(R.id.ck_btn_2)
    CheckBox ck_btn_2;
    @BindView(R.id.ck_btn_3)
    CheckBox ck_btn_3;
    @BindView(R.id.ed_input)
    EditText ed_input;
    @BindView(R.id.tv_countSize)
    TextView tv_countSize;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.root_re)
    RelativeLayout root_re;

    @Override
    public Feedback_Fragment_C.Presenter getPresenter() {
        return p;
    }

    public static Fragment_Feedback newInstance() {
        return new Fragment_Feedback();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_feed_back;
    }

    @Override
    public void initView() {
        root_re.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        ck_btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_input.setCursorVisible(false);
                ed_input.setFocusable(false);
                ed_input.setFocusableInTouchMode(false);
            }
        });
        ck_btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_input.setCursorVisible(false);
                ed_input.setFocusable(false);
                ed_input.setFocusableInTouchMode(false);
            }
        });
        ck_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_input.setCursorVisible(false);
                ed_input.setFocusable(false);
                ed_input.setFocusableInTouchMode(false);
            }
        });

    }

    @Override
    public void initData() {
        ed_input.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ed_input.setFocusable(true);
                ed_input.setCursorVisible(true);
                ed_input.setFocusableInTouchMode(true);
                return false;
            }
        });
        ed_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable) && !editable.toString().equals("")) {
                    tv_countSize.setText(editable.toString().length() + "/300");
                } else {
                    tv_countSize.setText("0/300");
                }
            }
        });
    }

    @Override
    public void sendSuggestionSuccess(FeedBack feedBack) {

    }

    @Override
    public void sendSuggestionFailure(String failure, int code) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                ed_input.setCursorVisible(false);
                ed_input.setFocusable(false);
                ed_input.setFocusableInTouchMode(false);
                if (ck_btn_1.isChecked() || ck_btn_2.isChecked() || ck_btn_3.isChecked()) {

                } else {
                    ToastUtils.showLong("反馈的问题点为必选");
                    return;
                }
                if (ed_input.getText().toString().equals("") && (ed_input.getText().toString().length() < 10 || ed_input.getText().toString().length() > 300)) {
                    ToastUtils.showLong("长度限制10-300字");
                }

                break;
            case R.id.root_re:
                ed_input.setCursorVisible(false);
                ed_input.setFocusable(false);
                ed_input.setFocusableInTouchMode(false);
                break;
        }
    }
}
