package com.payu.hackerearth.kickstart.Activities;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


import com.payu.hackerearth.kickstart.Config;
import com.payu.hackerearth.kickstart.R;

/**
 * Created by Sibasish Mohanty on 12/08/17.
 */

public class ProjectSiteActivity extends AppCompatActivity {


    WebView postView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_site);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Project Viewer");
        progressBar = (ProgressBar) findViewById(R.id.progressBar) ;
        progressBar.setMax(100);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);

        Bundle extras = getIntent().getExtras();

        String url = extras.getString("project", Config.KICKSTARTER_URL);
        String link = Config.KICKSTARTER_URL+url;
        initWebview(link);
    }

    protected void initWebview(String url){
        postView = (WebView) findViewById(R.id.webview);
        postView.getSettings().setJavaScriptEnabled(true);

        postView.getSettings().setSupportZoom(true);

        postView.setVerticalScrollBarEnabled(false);
        postView.setHorizontalScrollBarEnabled(false);
        postView.setWebViewClient(new PostViewClient());
        postView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                progressBar.setProgress(newProgress);
                if (newProgress==100){
                    progressBar.setVisibility(View.GONE);

                }
                super.onProgressChanged(view, newProgress);
            }
        });
        postView.loadUrl(url);
    }

    protected class PostViewClient extends WebViewClient {
        PostViewClient() {
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            //view.setVisibility(View.VISIBLE);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
          //  view.setVisibility(View.GONE);
            //view.scrollTo(0,0);
            super.onPageStarted(view, url, favicon);
        }
    }
    @Override
    public void onBackPressed() {
        if (postView.canGoBack()){
            postView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id==android.R.id.home) {
            super.onBackPressed();
        }
        return true;
    }

}
