package com.coahr.thoughtrui.mvp.view.TimeService;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.coahr.thoughtrui.IMyAidlInterface;
import com.socks.library.KLog;

import androidx.annotation.Nullable;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/14
 * 描述：
 */
public class LocalService extends Service {
    MyBinder binder;
    MyConn conn;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new MyBinder();
        conn = new MyConn();
    }

    class MyBinder extends IMyAidlInterface.Stub {
        @Override
        public String getServiceName() throws RemoteException {
            return LocalService.class.getSimpleName();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.bindService(new Intent(LocalService.this, RomoteService.class), conn, Context.BIND_IMPORTANT);
        AlarmTimerUtil instance = AlarmTimerUtil.getInstance(this);
        instance.createGetUpAlarmManager(this,"TIMER_ACTION",10);
        instance.getUpAlarmManagerStartWork();
        return START_STICKY;
    }

    class MyConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //开启远程服务
            LocalService.this.startService(new Intent(LocalService.this, RomoteService.class));
            //绑定远程服务
            LocalService.this.bindService(new Intent(LocalService.this, RomoteService.class), conn, Context.BIND_IMPORTANT);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //开启远程服务
        LocalService.this.startService(new Intent(LocalService.this, RomoteService.class));
        //绑定远程服务
        LocalService.this.bindService(new Intent(LocalService.this, RomoteService.class), conn, Context.BIND_IMPORTANT);

    }
}
