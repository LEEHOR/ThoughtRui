package com.coahr.thoughtrui.widgets.AltDialog;

import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.KeyBoardUtils;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseDialogFragment;
import com.coahr.thoughtrui.widgets.Keyboard.SoftKeyboardStateHelper;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/25
 * 描述：填空题键盘
 */
public class Fill_in_blankDialog extends BaseDialogFragment {
    @BindView(R.id.tv_view)
    View tv_view;
    @BindView(R.id.lin_all)
    LinearLayout lin_all;
    @BindView(R.id.ed_input)
    EditText ed_input;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    private InPutOnClick onClick;
    private SoftKeyboardStateHelper softKeyboardStateHelper;

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    public static Fill_in_blankDialog newInstance() {
        return new Fill_in_blankDialog();
    }

    @Override
    public int bindLayout() {
        return R.layout.dialog_fill_in_blank;
    }

    @Override
    public void initView() {
        KeyBoardUtils.UpdateUI(lin_all, getActivity());
        KeyBoardUtils.showKeybord(ed_input, getActivity());
        tv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(ed_input.getText()) || !ed_input.getText().toString().equals("") || ed_input.getText().toString().length()>3) {
                    KeyBoardUtils.hideKeybord(ed_input, getActivity());
                    dismiss();
                    if (onClick != null) {
                        onClick.setOnClick(ed_input.getText().toString());
                    }
                } else {
                    if (onClick != null) {
                        onClick.setOnClickFailure();
                    }
                }
            }
        });
        softKeyboardStateHelper = new SoftKeyboardStateHelper(lin_all);
        softKeyboardStateHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {

            }

            @Override
            public void onSoftKeyboardClosed() {
                KeyBoardUtils.hideKeybord(ed_input, getActivity());
                dismiss();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(ed_input.getText()) && !ed_input.getText().toString().equals("")) {
                    KeyBoardUtils.hideKeybord(ed_input, getActivity());
                    dismiss();
                    if (onClick != null) {
                        onClick.setOnClick(ed_input.getText().toString());
                    }
                } else {
                    if (onClick != null) {
                        onClick.setOnClickFailure();
                    }
                }
            }
        });

    }

    @Override
    public void initData() {
        ed_input.setFocusableInTouchMode(true);
        ed_input.setFocusable(true);
        ed_input.requestFocus();
        ed_input.findFocus();
    }



    @Override
    public void iniWidow(Window window) {
        if (window != null) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(lp.MATCH_PARENT, lp.MATCH_PARENT);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            window.setWindowAnimations(R.style.bottom_in_out_animation);
        }
    }

    public interface InPutOnClick {
        void setOnClick(String text);

        void setOnClickFailure();
    }

    public void setOnClick(InPutOnClick onClick) {
        this.onClick = onClick;
    }
}
