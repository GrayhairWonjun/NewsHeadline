package myds.wjang.newsheadline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class StoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        WebView storyViewer = (WebView) findViewById(R.id.wvStoryView);
        storyViewer.setWebViewClient(new WebViewClient());
        WebSettings webSettings = storyViewer.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        storyViewer.loadUrl(bundle.getString("story_url"));
    }

}
