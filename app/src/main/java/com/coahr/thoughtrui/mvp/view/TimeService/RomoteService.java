package com.coahr.thoughtrui.mvp.view.TimeService;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
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
public class RomoteService extends Service {
    MyConn conn;
    MyBinder binder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        conn = new MyConn();
        binder = new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.bindService(new Intent(this, LocalService.class), conn, Context.BIND_IMPORTANT);

        return START_STICKY;
    }

    class MyBinder extends IMyAidlInterface.Stub {
        @Override
        public String getServiceName() throws RemoteException {
            return RomoteService.class.getSimpleName();
        }
    }

    class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //开启本地服务

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                RomoteService.this.startForegroundService(new Intent(RomoteService.this, LocalService.class));
            } else {
                RomoteService.this.startService(new Intent(RomoteService.this, LocalService.class));
            }
            //绑定本地服务
            RomoteService.this.bindService(new Intent(RomoteService.this, LocalService.class), conn, Context.BIND_IMPORTANT);

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //开启本地服务
       // RomoteService.this.startService(new Intent(RomoteService.this, LocalService.class));
        //绑定本地服务
       // RomoteService.this.bindService(new Intent(RomoteService.this, LocalService.class), conn, Context.BIND_IMPORTANT);

    }

}
