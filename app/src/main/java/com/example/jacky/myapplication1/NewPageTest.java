package com.example.jacky.myapplication1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import com.google.firebase.analytics.FirebaseAnalytics;


import com.appsflyer.AppsFlyerLib;

public class NewPageTest extends AppCompatActivity {
    private static final String TAG = "AppsFlyer";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //AppsFlyerLib.getInstance().sendDeepLinkData(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page_test);
        Intent intent = getIntent();
        //private static final String TAG = "MainActivity";
        Log.e(TAG, "scheme:" + intent.getScheme());
        Uri uri = intent.getData();
        Log.e(TAG, "scheme: " + uri.getScheme());
        Log.e(TAG, "host: " + uri.getHost());
        Log.e(TAG, "port: " + uri.getPort());
        Log.e(TAG, "path: " + uri.getPath());
        Log.e(TAG, "queryString: " + uri.getQuery());
        Log.e(TAG, "queryParameter: " + uri.getQueryParameter("key"));
        String s1 = uri.getQuery();
        TextView t1 = (TextView)findViewById(R.id.editText2);
        t1.setText(s1);
        //AppsFlyerLib.getInstance().sendDeepLinkData(this);
        AppsFlyerLib.getInstance().sendPushNotificationData(this);
        //AppsFlyerLib.getInstance().sendDeepLinkData(this);

}
}
