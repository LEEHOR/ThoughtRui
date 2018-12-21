package com.coahr.thoughtrui.widgets.AltDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.KeyBoardUtils;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.widgets.Keyboard.AnFQNumEditText;
import com.coahr.thoughtrui.widgets.Keyboard.KeyboardChangeListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author： hengzwd on 2018/8/2.
 * Email：hengzwdhengzwd@qq.com
 */
public class EvaluateInputDialogFragment extends AppCompatDialogFragment {

    @BindView(R.id.fl_evaluate_input)
    FrameLayout flEvaluateInput;
    @BindView(R.id.et_input)
    AnFQNumEditText etInput;
    @BindView(R.id.tv_send)
    TextView tvSend;
    Unbinder unbinder;

    private InputCallback inputCallback;

    private KeyboardChangeListener keyboardChangeListener;

    public static EvaluateInputDialogFragment newInstance() {
        return new EvaluateInputDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentdialog_evaluate_input, container, false);
        unbinder = ButterKnife.bind(this, view);
        KeyBoardUtils.UpdateUI(view.getRootView(), getActivity());
        init();
        return view;
    }


    private void init() {
        KeyBoardUtils.showKeybord(etInput.getEtContent(), getActivity());
        keyboardChangeListener = new KeyboardChangeListener(this.getDialog().getWindow());
        keyboardChangeListener.setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
              //  KLog.e("isshow:" + isShow + "keyboardheigth" + keyboardHeight);
                if (!isShow) {
                  //  dismiss();
                }
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setWindowAnimations(R.style.bottom_in_out_animation);
        }
        return dialog;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.tv_send, R.id.fl_evaluate_input})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send:

                if (etInput.getText() != null && !etInput.getText().equals("")) {
                    inputCallback.onInputSend(etInput.getText(),this);
                } else {
                    Toast.makeText(BaseApplication.mContext, "请输入内容", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.fl_evaluate_input:
                dismiss();
                break;
        }
    }

    public void setOnInputCallback(InputCallback inputCallback) {
        this.inputCallback = inputCallback;
    }

    public interface InputCallback {
        void onInputSend(String input, AppCompatDialogFragment dialog);
    }


}
