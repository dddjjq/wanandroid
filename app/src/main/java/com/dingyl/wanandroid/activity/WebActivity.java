package com.dingyl.wanandroid.activity;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dingyl.wanandroid.R;

public class WebActivity extends AppCompatActivity {

    private static final String TAG = "WebActivity";
    private WebView webView;
    private ProgressBar webProgress;
    private String url;
    private String title;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        initLoad();
    }

    private void initView(){
        webView = findViewById(R.id.web_view);
        webProgress = findViewById(R.id.web_progress);
        url = getIntent().getStringExtra("web_url");
        title = getIntent().getStringExtra("web_title");
        actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @JavascriptInterface
    private void initLoad(){
        webView.loadUrl(url);
        webView.addJavascriptInterface(this,"android");
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
    }

    private WebViewClient webViewClient = new WebViewClient(){
        @Override
        public void onPageFinished(WebView webView,String url){
            webProgress.setVisibility(View.GONE);
            Log.d(TAG,"onPageFinished");
        }

        @Override
        public void onPageStarted(WebView webView, String url, Bitmap favicon){
            webProgress.setVisibility(View.VISIBLE);
            Log.d(TAG,"onPageStarted");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.equals("http://www.google.com/")){
                Toast.makeText(WebActivity.this,"国内不能访问google,拦截该url",Toast.LENGTH_LONG).show();
                return true;//表示我已经处理过了
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient(){

        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult jsResult){
            AlertDialog.Builder builder = new AlertDialog.Builder(webView.getContext());
            builder.setMessage(message);
            builder.setCancelable(false);
            builder.create().show();
            jsResult.confirm();//处理结果为确定状态同时唤醒WebCore线程,处理结果为确定状态同时唤醒WebCore线程
            return true;
        }

        @Override
        public void onReceivedTitle(WebView webView,String title){
            super.onReceivedTitle(webView,title);
        }

        @Override
        public void onProgressChanged(WebView webView,int newProgress){
            webProgress.setProgress(newProgress);
            Log.d(TAG,"onProgressChanged");
            Log.d(TAG,"newProgress is : " + newProgress);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
