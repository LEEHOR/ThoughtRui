package com.coahr.thoughtrui.mvp.view.mydata;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.Feedback_Fragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.FeedBack;
import com.coahr.thoughtrui.mvp.presenter.Feedback_Fragment_P;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

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
    @BindView(R.id.rg_group)
    RadioGroup radioGroup;
    @BindView(R.id.rb_1)
    RadioButton rb_1;
    @BindView(R.id.rb_2)
    RadioButton rb_2;
    @BindView(R.id.rb_3)
    RadioButton rb_3;
    @BindView(R.id.ed_input)
    EditText ed_input;
    @BindView(R.id.tv_countSize)
    TextView tv_countSize;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.feedback_tittle)
    MyTittleBar feedback_tittle;
    private String type;

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
        tv_submit.setOnClickListener(this);
        feedback_tittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void initData() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ed_input.setCursorVisible(false);
                ed_input.setFocusable(false);
                ed_input.setFocusableInTouchMode(false);
                if (i == R.id.rb_1) {
                    type = "1";
                }
                if (i == R.id.rb_2) {
                    type = "2";
                }
                if (i == R.id.rb_3) {
                    type = "3";
                }
            }
        });
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
        ToastUtils.showLong(feedBack.getMsg());
        tv_submit.setBackgroundResource(R.drawable.bg_gray_white_frame_background);
    }

    @Override
    public void sendSuggestionFailure(String failure, int code) {
        ToastUtils.showLong(failure);
        tv_submit.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                ed_input.setCursorVisible(false);
                ed_input.setFocusable(false);
                ed_input.setFocusableInTouchMode(false);
                if (rb_1.isChecked() || rb_2.isChecked() || rb_3.isChecked()) {

                } else {
                    ToastUtils.showLong(getResources().getString(R.string.feed_back_is_required));
                    return;
                }

                if (ed_input.getText().toString().equals("") && (ed_input.getText().toString().length() < 10 || ed_input.getText().toString().length() > 300)) {
                    ToastUtils.showLong(getResources().getString(R.string.feed_back_length_limited));
                    return;
                }
                getOptions(type, ed_input.getText().toString().trim());
                tv_submit.setEnabled(false);
                break;
            case R.id.root_re:
                ed_input.setCursorVisible(false);
                ed_input.setFocusable(false);
                ed_input.setFocusableInTouchMode(false);
                break;
        }
    }

    private void getOptions(String type, String content) {
        Map map = new HashMap();
        map.put("sessionId", Constants.sessionId);
        map.put("type", type);
        map.put("content", content);
        p.sendSuggestion(map);
    }
}
