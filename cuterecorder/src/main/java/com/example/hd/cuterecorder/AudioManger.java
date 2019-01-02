package com.example.hd.cuterecorder;

import android.app.NotificationManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by LHD on 2016/8/30.
 */
public class AudioManger {
    private MediaRecorder mMediaRecorder;
    private String mDir;
    private String mCurrentFilePath;
    private String audioName;
    private String audioPath_tem;
    private String audioPathPath;
    private static AudioManger mInstance;
    private boolean isPrepared;
    private int START_RECORDER = 1;
    private int STOP_RECORDER = 2;
    private int PAUSE_RECORDER = 3;
    private int RecorderStatus; //当前录音状态
    private NotificationManager manager;
    private int anInt;




    public interface AudioStateListener {
        void wellPrepared();
    }

    public AudioStateListener mListener;

    public void setOnAudioStateListener(AudioStateListener Listener) {
        this.mListener = Listener;
    }

    public static AudioManger getInstance() {
        if (mInstance == null) {
            synchronized (AudioManger.class) {
                if (mInstance == null) {
                    mInstance = new AudioManger();
                }
            }
        }
        return mInstance;
    }
    //prepare to start
    public void prepareAudio() {
        String fileName;
        File file;
        try {
            isPrepared = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (getAudioName() != null) {
                    fileName = getAudioName();
                } else {
                    fileName = generateFileName();
                }
                //保存的正式路径
                if (getAudioPath() !=null){
                    File audioPath = new File(getAudioPath());
                    if (!audioPath.exists()) {
                        audioPath.mkdirs();
                    }
                    file = new File(getAudioPath(), fileName);
                } else {  //默认路径
                    File dir = new File(getmDir());
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    file = new File(dir, fileName);
                }
            } else {
                fileName = generateFileName();
                    //临时文件夹
                if (getAudioPath_tem() !=null){
                    File audioPath_tem = new File(getAudioPath_tem());
                    if (!audioPath_tem.exists()) {
                        audioPath_tem.mkdirs();
                    }
                    file = new File(getAudioPath_tem(), fileName);
                } else {
                    File dir = new File(getmDir());
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    file = new File(dir, fileName);
                }
            }
            mCurrentFilePath = file.getAbsolutePath();

            mMediaRecorder = new MediaRecorder();
            //set output file
            mMediaRecorder.setOutputFile(mCurrentFilePath);
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.prepare();

            isPrepared = true;
            if (mListener != null) {
                mListener.wellPrepared();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (isPrepared &&  RecorderStatus!=START_RECORDER) {
            RecorderStatus = START_RECORDER;
            mMediaRecorder.start();
        }
        Log.e("录音类","录音开始");
    }

    public String generateFileName() {
        Log.e("录音类","自定义录音文件名："+System.currentTimeMillis() + ".amr");
        return System.currentTimeMillis() + ".amr";
    }

    public void release() {
        if (mMediaRecorder != null) {
            RecorderStatus = STOP_RECORDER;
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }
    //暂停>24(andriod>24)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void pause_new() {
        if (mMediaRecorder != null) {
            if (isPrepared && RecorderStatus == START_RECORDER) {  //当前是否为录音状态
                mMediaRecorder.pause();
            }
            RecorderStatus = PAUSE_RECORDER;
        }
    }
    //暂停
    public void pause_old(){
        if (mMediaRecorder != null) {
            if (isPrepared && RecorderStatus == START_RECORDER) {  //当前是否为录音状态
                release();
            }
        }
    }

    //继续 （android>24）
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void resume_new() {
        if (mMediaRecorder != null && isPrepared) {
            if (isPrepared && RecorderStatus == PAUSE_RECORDER) {
                    RecorderStatus = START_RECORDER;
                    mMediaRecorder.resume();
            }
        }
    }

    //继续
    public void resume_old() {
        if (mMediaRecorder != null && isPrepared) {
            if (isPrepared && RecorderStatus == PAUSE_RECORDER) {
                RecorderStatus = START_RECORDER;
                 start();
            }
        }
    }

    public void cancel() {
        release();
        //如果取消就删除生成的录音文件
        if (mCurrentFilePath != null) {
            File file = new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath = null;
        }
    }

    public int getVoiceLevel(int maxLevel) {
        if (isPrepared) {
            try {
                //mMediaRecorder.getMaxAmplitude() 1-32767
                return maxLevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (Exception e) {

            }
        }
        return 1;
    }

    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }


    /**
     * 录音文件名
     * 每次都会重置为null,
     * 默认为时间戳
     *
     * @param AudioName
     */
    public void setAudioName(String AudioName) {

        if (AudioName != null) {
            this.audioName = AudioName + ".amr";
            Log.e("录音类","自定义录音文件名："+audioName);
        } else {
            this.audioName = null;
        }

    }

    public String getAudioName() {
        return audioName;
    }

    public String getAudioPath_tem() {
        return audioPath_tem;
    }

    public void setAudioPath_tem(String audioPath_tem) {
        this.audioPath_tem = audioPath_tem;
        Log.e("录音类","录音保存临时路径："+audioPath_tem);
    }

    public String getAudioPath() {
        return audioPathPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPathPath = audioPath;
        Log.e("录音类","录音保存正时路径："+audioPath);
    }

    public String getmDir() {
        return mDir;
    }

    public void setmDir(String mDir) {
        this.mDir = mDir;
    }


/*    *//**
     * 创建通知
     * @param activity
     *//*
    public void CreatedNotification(Activity activity){
        manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(AudioManger.this,activity.getClass());
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"recorder");
        //通知栏显示内容
        builder.setTicker("notify_activity");
        //通知消息下拉是显示的文本内容
        builder.setContentText("content");
        //通知栏消息下拉时显示的标题
        builder.setContentTitle("title");
        //接收到通知时，按手机的默认设置进行处理，声音，震动，灯
        builder.setDefaults(Notification.DEFAULT_ALL);
        //通知栏显示图标
        builder.setSmallIcon(R.drawable.notification_template_icon_bg);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        //点击跳转后消失
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(1,notification);
    }*/

    /**
     * 创建服务通知
     * @param activity
     */
   /* public void getNotification(Activity activity){
        Intent intent = new Intent(AudioManger.this,activity.getClass());
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
        anInt = new Random(System.nanoTime()).nextInt();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder mBuilder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建渠道组
            String groupId = "recorder_notification";
            String groupName = "录音服务";
            NotificationChannelGroup group = new NotificationChannelGroup(groupId, groupName);
            manager.createNotificationChannelGroup(group);
            //创建通知消息
            String Notification_id = "录音服务";
            // 用户可以看到的通知渠道的名字.
            CharSequence Notification_name ="录音服务";
            // 用户可以看到的通知渠道的描述
            String Notification_description ="录音服务";
            NotificationChannel channel = new NotificationChannel(Notification_id, Notification_name, NotificationManager.IMPORTANCE_HIGH);
            if (manager != null) {
                // 配置通知渠道的属性
                channel.setGroup(groupId);
                channel.setDescription(Notification_description);
                    // 设置通知出现时的闪灯（如果 android 设备支持的话）
                    channel.enableLights(true);
                    channel.setLightColor(Color.RED);
                    // 设置通知出现时的震动（如果 android 设备支持的话）
                  //  channel.enableVibration(true);
                   // channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                manager.createNotificationChannel(channel);
            }
            mBuilder = new Notification.Builder(getApplicationContext(), Notification_id);
            // mBuilder.setCustomContentView(myNotificationView);
            mBuilder.setWhen(System.currentTimeMillis())
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.notification_template_icon_bg))
                    .setSmallIcon(R.drawable.notification_template_icon_bg)
                    .setContentTitle("调查系统")
                    .setContentText("正在录音")
                    .setTicker("调查系统正在录音")
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent)
                    .setChannelId(Notification_id);

        } else {
            mBuilder = new Notification.Builder(getApplicationContext());
            mBuilder.setSmallIcon(R.drawable.notification_template_icon_bg)
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.notification_template_icon_bg))
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle("调查系统")
                    .setContentText("正在录音")
                    .setContentIntent(pendingIntent)
                    .setTicker("调查系统正在录音");

        }
        Notification notification = mBuilder.build();
        manager.notify(anInt, notification);
    }*/

    /**
     * 关闭服务
     */
    public void close_notification(){
        manager.cancel(anInt);
    }
}
