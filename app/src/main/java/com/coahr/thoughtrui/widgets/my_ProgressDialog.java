package com.coahr.thoughtrui.widgets;

import android.app.ProgressDialog;
import android.content.Context;

import com.coahr.thoughtrui.R;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/19
 * 描述：进度对话框
 */
public class my_ProgressDialog {
  public static void setProgress(Context context){
      ProgressDialog pd=new ProgressDialog(context);
      pd.setTitle("我是一个进度条对话框");//设置一个标题
      pd.setMessage("正在下载当中...");//设置消息
      pd.setIcon(R.mipmap.ico_dsc);//设置一个图标
      pd.setMax(100);//设置进度条的最大值
      pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);;
      pd.setProgress(30);//设置进度条的当前进度
      pd.setCancelable(true);//这是是否可撤销/也就是这个对话框是否可以关闭
      pd.setIndeterminate(false);//设置是否是确定值
      pd.show();//展示对话框
  }
}
