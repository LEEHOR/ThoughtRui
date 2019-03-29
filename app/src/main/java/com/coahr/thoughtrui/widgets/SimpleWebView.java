package com.coahr.thoughtrui.widgets;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;

import com.coahr.thoughtrui.R;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/28
 * 描述：
 */
public class SimpleWebView extends WebView {

    public SimpleWebView(Context context) {
        super(context);
        init();
    }

    public SimpleWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public SimpleWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        this.setWebViewClient(new SimpleWebViewClient());

        this.setWebChromeClient(new WebChromeClient(){
            //这里可以设置进度条。但我是用另外一种
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
            }
        });
    }

    public static class SimpleWebViewClient extends com.tencent.smtt.sdk.WebViewClient {

        private Dialog loadingDialog;

        @Override
        public com.tencent.smtt.export.external.interfaces.WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
         /*   //做广告拦截，ADFIlterTool 为广告拦截工具类
            if (!ADFilterTool.hasAd(webView.getContext(),url)){
                return super.shouldInterceptRequest(webView, url);
            }else {

            }*/
            return super.shouldInterceptRequest(webView, url);
        }
/* * 防止加载网页时调起系统浏览器
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
           // webView.loadUrl(url);
            return super.shouldOverrideUrlLoading(webView, url);
        }
        //在开始的时候，开始loadingDialog
        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {

            try{
                loadingDialog = new Dialog (webView.getContext(), R.style.dialog_loading);
                loadingDialog.setContentView(R.layout.dialog_loading_layout);
                loadingDialog.setCanceledOnTouchOutside(false);
                loadingDialog.setCancelable(false);
                loadingDialog.show();
            }catch (Exception e){

            }
            super.onPageStarted(webView, s, bitmap);
        }
        //在页面加载结束的时候，关闭LoadingDialog
        @Override
        public void onPageFinished(WebView webView, String s) {

            try {
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
            } catch (Exception e) {

            }
            super.onPageFinished(webView, s);
        }

        @Override
        public void onReceivedError(WebView webView, com.tencent.smtt.export.external.interfaces.WebResourceRequest webResourceRequest, com.tencent.smtt.export.external.interfaces.WebResourceError webResourceError) {
            super.onReceivedError(webView, webResourceRequest, webResourceError);
        }

        @Override
        public void onReceivedSslError(WebView webView, com.tencent.smtt.export.external.interfaces.SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
            sslErrorHandler.proceed();
        }

    }

    /**
     * 清除X5Cookie
     *
     * @param context
     */
    public static void clearX5Cookie(Context context){
        com.tencent.smtt.sdk.CookieSyncManager.createInstance(context);  //Create a singleton CookieSyncManager within a context
        com.tencent.smtt.sdk.CookieManager cookieManager = com.tencent.smtt.sdk.CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// forces sync manager to sync now
            com.tencent.smtt.sdk.CookieManager.getInstance().flush();
        } else {
            com.tencent.smtt.sdk.CookieSyncManager.createInstance(context);
            com.tencent.smtt.sdk.CookieSyncManager.getInstance().sync();
        }
    }

}
