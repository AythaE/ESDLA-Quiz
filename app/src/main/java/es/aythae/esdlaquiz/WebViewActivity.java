/*
 * File: WebViewActivity.java
 * Project: ESDLA Quiz
 *
 * Author: Aythami Estévez Olivas
 * Email: aythae[at]gmail[dot]com
 * Date: 31-ene-2017
 * Repository: https://github.com/AythaE/ESDLA-Quiz
 * License: GPL-3.0
 */

package es.aythae.esdlaquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Class that handle a simple UI that show a WebView to the user
 */
public class WebViewActivity extends AppCompatActivity {

    public static final String OTHERS_GAMES_URL="http://m.silvergames.com/es";

    private WebView webView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Log.d(this.getClass().getSimpleName(), "onCreate: ");

        webView = new WebView(WebViewActivity.this);
        webView.getSettings().getJavaScriptEnabled();
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(OTHERS_GAMES_URL);

        setContentView(webView);

    }

    /**
     * If the webview could go back to a previous visited direction then go back, if not return to
     * MainActivity
     */
    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

}
