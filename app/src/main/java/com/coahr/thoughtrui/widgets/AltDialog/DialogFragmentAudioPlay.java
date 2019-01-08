package com.coahr.thoughtrui.widgets.AltDialog;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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

import java.io.IOException;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/12/27
 * on 9:18
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
    private MediaPlayer mediaPlayer;
    private String audioPath;
    private boolean isPrepare; //是否准备
    private int PlayType=1;
    private final int AUDIO_PLAY=1;  //开始播放
    private final int AUDIO_PAUSE=2; //暂停播放
    private final int AUDIO_RESUME=3; //继续播放
    private final int AUDIO_STOP=4;  //停止播放
    private  final  int UPDATE_AUDIO_TIME=1;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==UPDATE_AUDIO_TIME) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                //设置播放时间
                updateTime(tv_play_point,tv_play_count,currentPosition,duration);
                //设置播放进度
                play_seekBar.setMax(duration);
                play_seekBar.setProgress(currentPosition);
                mHandler.sendEmptyMessageDelayed(UPDATE_AUDIO_TIME, 500);
            }
        }

    };

    public static DialogFragmentAudioPlay newInstance(String audioPath) {
        DialogFragmentAudioPlay audioPlay=new DialogFragmentAudioPlay();
        Bundle bundle=new Bundle();
        bundle.putString("audioPath",audioPath);
        audioPlay.setArguments(bundle);
        return  audioPlay;
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
        play_button.setOnClickListener(this);
        stop_button.setOnClickListener(this);
        play_seekBar.setOnSeekBarChangeListener(new SeekBarChangeListenerS());
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            audioPath = getArguments().getString("audioPath");
        }
        if (audioPath !=null){
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
                        isPrepare=true;
                        int duration = mediaPlayer.getDuration();
                        //TimeUtils.updataTimeFormat(tv_play_count,duration);
                        updateTime(null,tv_play_count,0,duration);
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

    @Override
    public void initAnimate() {

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
            switch (view.getId()){
                case R.id.play_button:
                    if (mediaPlayer != null && isPrepare && PlayType==AUDIO_PLAY) {
                        mediaPlayer.start();
                        upDateUi(PlayType);
                    } else if (mediaPlayer != null && isPrepare && PlayType==AUDIO_PAUSE){
                        mediaPlayer.pause();
                        upDateUi(PlayType);
                    } else if (mediaPlayer != null && isPrepare && PlayType==AUDIO_RESUME){
                        mediaPlayer.start();
                        upDateUi(PlayType);
                    }
                    break;
                case R.id.stop_button:
                    if (mediaPlayer != null && isPrepare){
                        mediaPlayer.stop();
                        upDateUi(4);
                    }
                    break;
            }
    }

    class SeekBarChangeListenerS implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            //设置当前的播放时间
            updateTime(tv_play_point,null,i,0);
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
     * @param type
     */
    private void upDateUi(int type){
        if (type==1){  //开始播放
            play_button.setImageResource(R.mipmap.play_pause);
            stop_button.setImageResource(R.mipmap.play_stoping);
            PlayType=AUDIO_PAUSE;
            mHandler.sendEmptyMessage(UPDATE_AUDIO_TIME);
        }
        if (type==2){  //暂停播放
            play_button.setImageResource(R.mipmap.play_start);
            stop_button.setImageResource(R.mipmap.play_stoping);
            PlayType=AUDIO_RESUME;
            mHandler.removeMessages(UPDATE_AUDIO_TIME);
        }
        if (type==3){  //继续播放
            play_button.setImageResource(R.mipmap.play_pause);
            stop_button.setImageResource(R.mipmap.play_stoping);
            PlayType=AUDIO_STOP;
            mHandler.sendEmptyMessage(UPDATE_AUDIO_TIME);
        }
        if (type==4){  //停止播放
            play_button.setImageResource(R.mipmap.play_start);
            stop_button.setImageResource(R.mipmap.play_stop);
            PlayType=AUDIO_PLAY;
            mHandler.removeMessages(UPDATE_AUDIO_TIME);
            updateTime(tv_play_point,null,0,0);
        }
    }

    public interface mediaPlayListener{
        void onPlayComplete();
    }

    /**
     * 更新时间
     * @param audioPoint
     *      播放时间控件
     * @param audioCount
     *      总时间控件
     * @param pointTime
     *      播放位置
     * @param CountTime
     * 总时长
     */
    private void updateTime(TextView audioPoint,TextView audioCount,int pointTime,int CountTime){
        if (audioPoint != null) {
            TimeUtils.updataTimeFormat(audioPoint,pointTime);
        }
        if (audioCount!=null){
            TimeUtils.updataTimeFormat(audioCount,CountTime);
        }
    }
}