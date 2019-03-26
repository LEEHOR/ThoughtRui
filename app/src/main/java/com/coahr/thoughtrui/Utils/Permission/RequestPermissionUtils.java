package com.coahr.thoughtrui.Utils.Permission;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Setting;

import java.util.List;

/**
 * Created by Leehor
 * on 2018/10/26
 * on 11:34
 */
public class RequestPermissionUtils {

    /**
     * Request permissions.
     */
    public static   void requestPermission(final Context context, final OnRequestPermissionListener listener , String... permissions) {

        if (AndPermission.hasPermissions(context,permissions)){
            if (listener != null) {
                listener.PermissionHave();
            }
        } else {
            AndPermission.with(context)
                    .runtime()
                    .permission(permissions)
                    .rationale(new RuntimeRationale())
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> permissions) {
                            if (listener != null) {
                                listener.PermissionSuccess(permissions);
                            }
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(@NonNull List<String> permissions) {
                            showSettingDialog(context, permissions,listener);
                        }
                    })
                    .start();
        }
    }

    /**
     * Display setting dialog.
     */
    public  static  void showSettingDialog(final Context context, final List<String> permissions,OnRequestPermissionListener listener) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission(context);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.PermissionFail(permissions);
                        }
                    }
                })
                .show();
    }

    /**
     * Set permissions.
     */
    public static void setPermission(final Context context) {
        AndPermission.with(context)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {

                    }
                })
                .start();
    }

}
