package com.coahr.thoughtrui.mvp.view.WebView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.widgets.MyWebView;
import com.coahr.thoughtrui.widgets.SimpleWebView;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.socks.library.KLog;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/5
 * 描述：网页浏览
 */
public class Fragment_WebView extends BaseFragment {
    @BindView(R.id.myWebView)
    SimpleWebView myWebView;
    @BindView(R.id.WebViewTittle)
    MyTittleBar WebViewTittle;
    private String url;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    public static Fragment_WebView Instance(String url) {
        Fragment_WebView fragment_webView=new Fragment_WebView();
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        fragment_webView.setArguments(bundle);
        return fragment_webView;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_webview;
    }

    @Override
    public void initView() {
        if (getArguments() != null) {
            url = getArguments().getString("url");
        }
        initHardwareAccelerate();
    }

    @Override
    public void initData() {
        initDetailsH5(url);
      //  myWebView.loadUrl("http://61.183.131.58:8080/DFL_KM_GATEWAY/avicit/www/index.html?username_bGl3eA==");

    }

    @Override
    public void onDestroy() {

        myWebView.clearX5Cookie(_mActivity);
        myWebView.clearCache(true);

        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public boolean onBackPressedSupport() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
            return  true;
        }
        return super.onBackPressedSupport();
    }

    private void setPath() {
        RxGalleryFinalApi.setImgSaveRxDir(new File(Constants.SAVE_DIR_TAKE_PHOTO));
        RxGalleryFinalApi.setImgSaveRxCropDir(new File(Constants.SAVE_DIR_ZIP_PHOTO));//裁剪会自动生成路径；也可以手动设置裁剪的路径；
    }

    private void openMulti(int count) {
        setPath();
        RxGalleryFinal rxGalleryFinal = RxGalleryFinal
                .with(_mActivity)
                .image()
                .multiple();
        rxGalleryFinal.maxSize(count)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        List<MediaBean> mediaBeanList = imageMultipleResultEvent.getResult();
                        if (mediaBeanList != null && mediaBeanList.size() > 0) {
                            KLog.d(mediaBeanList.get(0).getOriginalPath());
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        Toast.makeText(BaseApplication.mContext, "OVER", Toast.LENGTH_SHORT).show();
                    }
                })
                .openGallery();
    }


    /**
     * 启用硬件加速
     */
    private void initHardwareAccelerate() {
        try {
            if (Build.VERSION.SDK_INT >= 16) {
                getActivity().getWindow().setFlags(
                        android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
    }

    private void initX5WebView(String url) {
        myWebView.getSettings().setDisplayZoomControls(false);
        myWebView.setScrollbarFadingEnabled(false);
        myWebView.loadUrl(url);
    }
    /**
     * 初始化webView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initDetailsH5(String url) {
        myWebView.addJavascriptInterface(new js(),"androidtojs");
        myWebView.getSettings().setDisplayZoomControls(true);
        myWebView.setWebViewClient(new SimpleWebView.SimpleWebViewClient(){
            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String url) {
                super.onPageFinished(webView, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String url) {
                webView.loadUrl(url);
                return true;
            }

        });

        myWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
                return super.onJsAlert(webView, s, s1, jsResult);
            }

            @Override
            public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
                return super.onJsConfirm(webView, s, s1, jsResult);
            }
        });
        myWebView.loadUrl(url);
    }

    /**
     * 交互
     */
    public class  js{
         @JavascriptInterface
        public  void selectImg(){
             mHandler.post(new Runnable() {
                 @Override
                 public void run() {
                     KLog.d("选择");
                 }
             });

           // openMulti(3);
        }

        @JavascriptInterface
        public void takeCamera(){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    KLog.d("拍照");
                }
            });
        }
    }

}
