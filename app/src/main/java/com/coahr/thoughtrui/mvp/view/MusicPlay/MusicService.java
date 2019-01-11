package com.coahr.thoughtrui.mvp.view.MusicPlay;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.coahr.thoughtrui.mvp.view.recorder.RecorderService;

public class MusicService extends Service {
    public MediaPlayer mediaPlayer;
    public boolean tag = false;
    private AudioManager ams;
    private TelephonyManager tm;
    private MyListener listener;
    private MusicPlayListener musicPlayListener;
  /*  public void init() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("/data/music.mp3");
            mediaPlayer.prepare();
            mediaPlayer.setLooping(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    //  通过 Binder 来保持 Activity 和 Service 的通信
    public MyBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

  /*  public void playOrPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }*/

 /*   public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource("/data/music.mp3");
                mediaPlayer.prepare();
                mediaPlayer.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        listener = new MyListener();
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        initAudio();
        super.onCreate();
    }
    //微信，qq通话监听
    private void initAudio() {
        ams = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ams.getMode();//这里getmode返回值为3时代表，接通qq或者微信电话
        ams.requestAudioFocus(mAudioFocusListener, 1, 1);
    }
    private AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            Log.d("qcl111", "focusChange----------" + focusChange);

            if (focusChange == 1) {//视频语音挂断状态

            } else {//微信或者qq语音视频接通状态
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                if (musicPlayListener != null) {
                    musicPlayListener.PausePlay();
                }
            }
        }
    };

    private class MyListener extends PhoneStateListener {

        // 当电话的呼叫状态发生变化的时候调用的方法
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            Log.d("qcl111", "state" + state);
            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE://空闲状态。
                        Log.v("myService", "空闲状态");
                        break;
                    case TelephonyManager.CALL_STATE_RINGING://铃响状态。
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                        }
                        if (musicPlayListener != null) {
                            musicPlayListener.PausePlay();
                        }
                        Log.v("myService", "铃响状态");
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK://通话状态

                        Log.v("myService", "通话状态");
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setMusicPlayListener(MusicPlayListener musicPlayListener) {
        this.musicPlayListener = musicPlayListener;
    }
}

