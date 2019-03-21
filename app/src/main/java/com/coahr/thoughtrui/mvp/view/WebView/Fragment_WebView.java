package com.coahr.thoughtrui.mvp.view.WebView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.widgets.MyWebView;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/5
 * 描述：网页浏览
 */
public class Fragment_WebView extends BaseFragment {
    @BindView(R.id.myWebView)
    MyWebView myWebView;
    @BindView(R.id.WebViewTittle)
    MyTittleBar WebViewTittle;
    private String url;

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
        myWebView.loadUrl(url);
    }

    @Override
    public void initData() {
        myWebView.setWebViewCallBack(new MyWebView.WebViewCallBack() {
            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith("http://www.google.com/")){
                        ToastUtils.showLong("国内不能访问google");
                    } else {
                        view.loadUrl(url);
                    }
            }

            @Override
            public void onJsAlert(WebView webView, String url, String message, JsResult result) {

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                WebViewTittle.getTvTittle().setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }

            @Override
            public void getClient(String str) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myWebView != null) {
            myWebView.clearCache(true);
            myWebView.clearHistory();
            myWebView.destroy();
        }

        myWebView=null;
    }

    @Override
    public void onPause() {
        super.onPause();
        myWebView.pauseTimers();
        myWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        myWebView.resumeTimers();
        myWebView.onResume();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
            return  true;
        }
        return super.onBackPressedSupport();
    }
}
