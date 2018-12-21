package com.coahr.thoughtrui.Utils;

import android.app.Activity;

import android.content.Context;
import android.view.Gravity;

import android.view.View;

import android.widget.Toast;

import com.coahr.thoughtrui.mvp.Base.BaseApplication;

public class ToastUtils {

	private static Toast mToast;
	private static Toast toast2;
	public static void showLong(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(BaseApplication.mContext, text, Toast.LENGTH_LONG);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}

	public static void showShort( String text) {
		if (mToast == null) {
			mToast = Toast.makeText(BaseApplication.mContext, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}

	public static void Toast_showImage(Activity activity, final String tvStr, final int imageResource, int  duration,View view){
		if (toast2 == null) {

			toast2 = new Toast(activity);

		}


		toast2.setView(view);

		toast2.setGravity(Gravity.CENTER, 0, 0);

		toast2.setDuration(duration);

		toast2.show();
	}

}
