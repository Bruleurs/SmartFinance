package com.Smartfinance.attraitbogalheiro.SmartFinance.news;

import android.webkit.WebChromeClient;
import android.webkit.WebView;


public class CustomWebChromeClient extends WebChromeClient {
    private ProgressListener mListener;

    public CustomWebChromeClient(ProgressListener listener) {
        mListener = listener;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        mListener.onUpdateProgress(newProgress);
        super.onProgressChanged(view, newProgress);
    }

    public interface ProgressListener {
        void onUpdateProgress(int progressValue);
    }
}