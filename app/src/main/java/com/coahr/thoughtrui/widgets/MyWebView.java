package com.coahr.thoughtrui.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/5
 * 描述：WebView
 */
public class MyWebView extends WebView {

  /*  //激活WebView为活跃状态，能正常执行网页的响应
webView.onResume() ;

        //当页面被失去焦点被切换到后台不可见状态，需要执行onPause
//通过onPause动作通知内核暂停所有的动作，比如DOM的解析、plugin的执行、JavaScript执行。
        webView.onPause();

        //当应用程序(存在webview)被切换到后台时，这个方法不仅仅针对当前的webview而是全局的全应用程序的webview
//它会暂停所有webview的layout，parsing，javascripttimer。降低CPU功耗。
        webView.pauseTimers()
//恢复pauseTimers状态
        webView.resumeTimers();*/
    private WebViewCallBack webViewCallBack;

    public MyWebView(Context context) {
        super(context);
        initWebView();
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWebView();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebView();
    }

    /**
     * 初始化
     */
    public void initWebView() {
        this.addJavascriptInterface(this, "android");//添加js监听 这样html就能调用客户端
        this.setWebChromeClient(webChromeClient);
        this.setWebViewClient(webViewClient);
        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        //webSettings.setAppCacheMaxSize(1024*1024*8);//设置缓冲大小，我设的是8M
    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            if (webViewCallBack != null) {
                webViewCallBack.onPageFinished(view, url);
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            if (webViewCallBack != null) {
                webViewCallBack.onPageStarted(view, url, favicon);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            /*if (url.equals("http://www.google.com/")) {

                return true;//表示我已经处理过了
            }*/
            if (webViewCallBack != null) {
                webViewCallBack.shouldOverrideUrlLoading(view, url);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

    };

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {

       /*     AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定",null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();*/
            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            if (webViewCallBack != null) {
                webViewCallBack.onJsAlert(webView, url, message, result);
            }
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (webViewCallBack != null) {
                webViewCallBack.onReceivedTitle(view, title);
            }
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (webViewCallBack != null) {
                webViewCallBack.onProgressChanged(view, newProgress);
            }
        }
    };


    /**
     * JS调用android的方法
     *
     * @param str
     * @return
     */
    @JavascriptInterface //仍然必不可少
    public void getClient(String str) {
        if (webViewCallBack != null) {
            webViewCallBack.getClient(str);
        }
    }



    public void setWebViewCallBack(WebViewCallBack webViewCallBack) {
        this.webViewCallBack = webViewCallBack;
    }

    public interface WebViewCallBack {
        void onPageFinished(WebView view, String url);

        void onPageStarted(WebView view, String url, Bitmap favicon);

        void shouldOverrideUrlLoading(WebView view, String url);

        void onJsAlert(WebView webView, String url, String message, JsResult result);

        void onReceivedTitle(WebView view, String title);

        void onProgressChanged(WebView view, int newProgress);

        void getClient(String str);

    }
}
