package com.coahr.thoughtrui.widgets.UIPlayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/19
 * 描述：Ui更新视图
 */
public class uiDisPlayer {
    private ProgressBar bar;
    private TextView infoView;
    private Activity activity;
    private Handler handler;

    private static final int DOWNLOAD_OK = 1;
    private static final int DOWNLOAD_FAIL = 2;
    private static final int UPLOAD_OK = 3;
    private static final int UPLOAD_FAIL = 4;
    private static final int UPDATE_PROGRESS = 5;
    private static final int DISPLAY_IMAGE = 6;
    private static final int DISPLAY_INFO = 7;

    private static final int SETTING_OK = 88;

    public  uiDisPlayer( ProgressBar bar, TextView infoView) {
        this.bar = bar;
        this.infoView = infoView;
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                String info;
                switch (inputMessage.what) {
                    case UPLOAD_OK:
                        break;
                    case UPLOAD_FAIL:
                        info = (String) inputMessage.obj;
                        break;
                    case DOWNLOAD_OK:
                        info = (String) inputMessage.obj;
                        uiDisPlayer.this.infoView.setText(info);
                        break;
                    case SETTING_OK:
                        break;
                    case DOWNLOAD_FAIL:
                        info = (String) inputMessage.obj;
                        break;
                    case UPDATE_PROGRESS:
                        uiDisPlayer.this.bar.setProgress(inputMessage.arg1);
                        //Log.d("UpdateProgress", String.valueOf(inputMessage.arg1));
                        break;
                    case DISPLAY_IMAGE:
                        break;
                    case DISPLAY_INFO:
                        info = (String) inputMessage.obj;
                        uiDisPlayer.this.infoView.setText(info);
                    default:
                        break;
                }
            }
        };
    }
    //上传信息
    public void uploadInfo(String info) {
        Message mes = handler.obtainMessage(DISPLAY_INFO,info);
        mes.sendToTarget();
    }
    //上传成功
    public void uploadComplete(String info) {
        Message mes = handler.obtainMessage(UPLOAD_OK,info);
        mes.sendToTarget();
    }

    //上传失败，显示对应的失败信息
    public void uploadFail(String info) {
        Message mes = handler.obtainMessage(UPLOAD_FAIL, info);
        mes.sendToTarget();
    }

    //更新进度，取值范围为[0,100]
    public void updateProgress(int progress) {
        //Log.d("UpdateProgress", String.valueOf(progress));
        if (progress > 100) {
            progress = 100;
        } else if (progress < 0) {
            progress = 0;
        }
        Message mes = handler.obtainMessage(UPDATE_PROGRESS, progress);
        mes.arg1 = progress;
        mes.sendToTarget();
    }
}
