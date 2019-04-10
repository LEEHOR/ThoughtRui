package com.coahr.thoughtrui.widgets.AltDialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.KeyBoardUtils;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.widgets.Keyboard.SoftKeyboardStateHelper;
import com.socks.library.KLog;

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
    LinearLayout flEvaluateInput;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.vie_view)
    View vie_view;
    @BindView(R.id.tv_send)
    TextView tvSend;
    Unbinder unbinder;

    private InputCallback inputCallback;
    private SoftKeyboardStateHelper softKeyboardStateHelper;
    private View view;


    public static EvaluateInputDialogFragment newInstance() {
        return new EvaluateInputDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragmentdialog_evaluate_input, container, false);
        unbinder = ButterKnife.bind(this, view);
        KeyBoardUtils.UpdateUI(view.getRootView(), getActivity());
        init();
        return view;
    }


    private void init() {
        KeyBoardUtils.showKeybord(etInput, getActivity());
        softKeyboardStateHelper = new SoftKeyboardStateHelper(view);
        softKeyboardStateHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {

            }

            @Override
            public void onSoftKeyboardClosed() {
                KeyBoardUtils.hideKeybord(etInput, getActivity());
                dismiss();
            }
        });
        vie_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.hideKeybord(etInput, getActivity());
                dismiss();
            }
        });
        etInput.setFocusableInTouchMode(true);
        etInput.setFocusable(true);
        etInput.requestFocus();
        etInput.findFocus();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(lp.MATCH_PARENT, lp.MATCH_PARENT);
            window.setWindowAnimations(R.style.bottom_in_out_animation);
        }
        return dialog;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                if (TextUtils.isEmpty(etInput.getText())) {
                    Toast.makeText(getActivity(), getString(R.string.toast_34), Toast.LENGTH_LONG).show();
                    return;
                }
                if (etInput.getText().length() > 50 || etInput.getText().length() <0) {
                    Toast.makeText(getActivity(), getString(R.string.toast_35), Toast.LENGTH_LONG).show();
                    return;
                }
                inputCallback.onInputSend(etInput.getText().toString(), this);
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
