package com.coahr.thoughtrui.widgets.AltDialog;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseDialogFragment;
import com.coahr.thoughtrui.mvp.view.musicPlay.MusicPlayListener;
import com.coahr.thoughtrui.mvp.view.musicPlay.MusicService;

import java.io.IOException;

import butterknife.BindView;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by Leehor
 * on 2018/12/27
 * on 9:18
 * 音频播放
 */
public class DialogFragmentAudioPlay extends BaseDialogFragment implements View.OnClickListener {
    @BindView(R.id.play_seekBar)
    SeekBar play_seekBar;
    @BindView(R.id.play_point)
    TextView tv_play_point;
    @BindView(R.id.play_count)
    TextView tv_play_count;
    @BindView(R.id.tv_audio_name)
    TextView tv_audio_name;
    @BindView(R.id.play_button)
    ImageView play_button;
    @BindView(R.id.stop_button)
    ImageView stop_button;
    @BindView(R.id.iv_close)
    ImageView iv_close;  //关闭
    private MediaPlayer mediaPlayer;
    private String audioPath;
    private boolean isPrepare; //是否准备
    private int PlayType = 1;
    private final int AUDIO_PLAY = 1;  //开始播放
    private final int AUDIO_PAUSE = 2; //暂停播放
    private final int AUDIO_RESUME = 3; //继续播放
    private final int AUDIO_STOP = 4;  //停止播放
    private final int UPDATE_AUDIO_TIME = 1;
    private MusicService musicService;
    //  回调onServiceConnected 函数，通过IBinder 获取 Service对象，实现Activity与 Service的绑定
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MyBinder) (service)).getService();
            musicService.setMusicPlayListener(new MusicPlayListener() {
                @Override
                public void PausePlay() {
                    if (mediaPlayer != null && isPrepare) {
                        mediaPlayer.stop();
                        release();
                        upDateUi(4);
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };
    private Intent intent;
    private String audioName;

    /**
     * 开启和绑定服务
     */
    private void startAndBindService() {
        intent = new Intent(getActivity(), MusicService.class);
        getActivity().startService(intent);
        getActivity().bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    /**
     * 关闭和解绑服务
     */
    private void unbindAndStopService() {
        getActivity().unbindService(serviceConnection);
        getActivity().stopService(intent);
    }

    //逻辑控制
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_AUDIO_TIME) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                //设置播放时间
                updateTime(tv_play_point, tv_play_count, currentPosition, duration);
                //设置播放进度
                play_seekBar.setMax(duration);
                play_seekBar.setProgress(currentPosition);
                mHandler.sendEmptyMessageDelayed(UPDATE_AUDIO_TIME, 500);
            }
        }

    };

    public static DialogFragmentAudioPlay newInstance(String audioPath, String audioName) {
        DialogFragmentAudioPlay audioPlay = new DialogFragmentAudioPlay();
        Bundle bundle = new Bundle();
        bundle.putString("audioPath", audioPath);
        bundle.putString("audioName", audioName);
        audioPlay.setArguments(bundle);
        return audioPlay;
    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.dialog_audio_play;
    }

    @Override
    public void initView() {
        startAndBindService();
        play_button.setOnClickListener(this);
        stop_button.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        play_seekBar.setOnSeekBarChangeListener(new SeekBarChangeListenerS());
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            audioPath = getArguments().getString("audioPath");
            audioName = getArguments().getString("audioName");
            tv_audio_name.setText(audioName);
        }
//        if (audioPath !=null){
//            mediaPlayer = new MediaPlayer();
//            try {
//                mediaPlayer.setDataSource(audioPath);
//
//                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mediaPlayer) {
//                                play_seekBar.setProgress(0);
//                                upDateUi(4);
//                    }
//                });
//                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//                    @Override
//                    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
//                        play_seekBar.setProgress(0);
//                        upDateUi(4);
//                        return false;
//                    }
//                });
//
//                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mediaPlayer) {
//                        isPrepare=true;
//                        int duration = mediaPlayer.getDuration();
//                        //TimeUtils.updataTimeFormat(tv_play_count,duration);
//                        updateTime(null,tv_play_count,0,duration);
//                        play_seekBar.setMax(duration);
//                    }
//                });
//                //是否循环播放
//                mediaPlayer.setLooping(false);
//                //准备
//                mediaPlayer.prepare();
//
//
//            } catch (IOException e) {
//
//                e.printStackTrace();
//            }
//
//        }
        prepare();
    }


    @Override
    public void iniWidow(Window window) {
        if (window != null) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(lp.MATCH_PARENT, lp.MATCH_PARENT);
            window.setWindowAnimations(R.style.bottom_in_out_animation);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_button:
                if (PlayType == AUDIO_PLAY) {
                    if (isPrepare) {
                        mediaPlayer.start();
                    } else {
                        prepare();
                        mediaPlayer.start();
                    }
                    upDateUi(PlayType);
                } else if (mediaPlayer != null && isPrepare && PlayType == AUDIO_PAUSE) {
                    mediaPlayer.pause();
                    upDateUi(PlayType);
                } else if (mediaPlayer != null && isPrepare && PlayType == AUDIO_RESUME) {
                    mediaPlayer.start();
                    upDateUi(PlayType);
                }
                break;
            case R.id.stop_button:
                if (mediaPlayer != null && isPrepare) {
                    mediaPlayer.stop();
                    release();
                    upDateUi(4);
                }
                break;
            case R.id.iv_close:
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    release();
                    upDateUi(4);
                }
                dismiss();
                break;
        }
    }

    class SeekBarChangeListenerS implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            //设置当前的播放时间
            updateTime(tv_play_point, null, i, 0);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            //拖动视频进度时，停止刷新
            mHandler.removeMessages(UPDATE_AUDIO_TIME);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (mediaPlayer != null && isPrepare) {
                //停止拖动后，获取拖动进度
                int totall = seekBar.getProgress();
                //设置VideoView的播放进度
                mediaPlayer.seekTo(totall);
                //重新handler刷新
                mHandler.sendEmptyMessage(UPDATE_AUDIO_TIME);
            }
        }
    }


    /**
     * ui更新
     *
     * @param type
     */
    private void upDateUi(int type) {
        if (type == 1) {  //开始播放
            play_button.setImageResource(R.mipmap.ic_pause);
            stop_button.setImageResource(R.mipmap.ic_stoping);
            PlayType = AUDIO_PAUSE;
            mHandler.sendEmptyMessage(UPDATE_AUDIO_TIME);
        }
        if (type == 2) {  //暂停播放
            play_button.setImageResource(R.mipmap.ic_play_circle_filled);
            stop_button.setImageResource(R.mipmap.ic_stoping);
            PlayType = AUDIO_RESUME;
            mHandler.removeMessages(UPDATE_AUDIO_TIME);
        }
        if (type == 3) {  //继续播放
            play_button.setImageResource(R.mipmap.ic_pause);
            stop_button.setImageResource(R.mipmap.ic_stoping);
            // PlayType=AUDIO_STOP;
            PlayType = AUDIO_PAUSE;
            mHandler.sendEmptyMessage(UPDATE_AUDIO_TIME);
        }
        if (type == 4) {  //停止播放
            play_button.setImageResource(R.mipmap.ic_play_circle_filled);
            stop_button.setImageResource(R.mipmap.ic_stop);
            PlayType = AUDIO_PLAY;
            mHandler.removeMessages(UPDATE_AUDIO_TIME);
            play_seekBar.setProgress(0);
            updateTime(tv_play_point, null, 0, 0);

        }
    }

    public interface mediaPlayListener {
        void onPlayComplete();
    }

    /**
     * 更新时间
     *
     * @param audioPoint 播放时间控件
     * @param audioCount 总时间控件
     * @param pointTime  播放位置
     * @param CountTime  总时长
     */
    private void updateTime(TextView audioPoint, TextView audioCount, int pointTime, int CountTime) {
        if (audioPoint != null) {
            TimeUtils.updataTimeFormat(audioPoint, pointTime);
        }
        if (audioCount != null) {
            TimeUtils.updataTimeFormat(audioCount, CountTime);
        }
    }

    @Override
    public void onDestroy() {
        unbindAndStopService();
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 释放
     */
    private void release() {
        if (mediaPlayer != null) {
            isPrepare = false;
            mediaPlayer.release();
            mediaPlayer.setOnCompletionListener(null);
            mediaPlayer.setOnErrorListener(null);
            mediaPlayer.setOnCompletionListener(null);
            mediaPlayer = null;
        }
    }

    /**
     * 准备
     */
    private void prepare() {
        if (audioPath != null && mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(audioPath);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        play_seekBar.setProgress(0);
                        upDateUi(4);
                    }
                });
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                        play_seekBar.setProgress(0);
                        upDateUi(4);
                        return false;
                    }
                });

                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        isPrepare = true;
                        int duration = mediaPlayer.getDuration();
                        //TimeUtils.updataTimeFormat(tv_play_count,duration);
                        updateTime(null, tv_play_count, 0, duration);
                        play_seekBar.setMax(duration);
                    }
                });
                //是否循环播放
                mediaPlayer.setLooping(false);
                //准备
                mediaPlayer.prepare();


            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    }
}