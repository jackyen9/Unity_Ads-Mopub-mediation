package com.example.jacky.myapplication1;

import android.Manifest;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Debug;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Button;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.CreateOneLinkHttpTask;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.appsflyer.share.CrossPromotionHelper;
import com.appsflyer.share.ShareInviteHelper;
import com.appsflyer.share.LinkGenerator;
import com.appsflyer.share.ShareInviteHelper;
import com.appsflyer.share.LinkGenerator;
import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import org.apache.http.util.EncodingUtils;
import org.jetbrains.annotations.NotNull;
//import org.apache.http.util.*;
import android.view.View;
//import com.google.android.gms.tagmanager.ContainerHolder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import com.huawei.hms.ads.identifier.AdvertisingIdClient;
import com.mopub.common.MoPub;
import com.mopub.common.MoPubReward;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.common.logging.MoPubLog;
import com.mopub.mobileads.MoPubRewardedAdListener;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.mopub.mobileads.unityads.*;
import com.mopub.common.util.Utils;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubRewardedAds;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.f2prateek.rx.preferences2.Preference;

import androidx.core.content.ContextCompat;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import android.widget.Toast;
import android.util.Log;
import android.telephony.TelephonyManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;


public class AppsFlyerTest extends AppCompatActivity implements MoPubInterstitial.InterstitialAdListener {
    MoPubInterstitial mInterstitial;
    private MoPubRewardedAdListener rewardedAdListener;
    void ＭopubShowVideo() {
        if ( MoPubRewardedAds.hasRewardedAd("40fd0bbc30e844e3b02cabffdc389385")) {
            MoPubRewardedAds.showRewardedAd("40fd0bbc30e844e3b02cabffdc389385");
            // mInterstitial.show();
        } else {
            // Caching is likely already in progress if `isReady()` is false.
            // Avoid calling `load()` here and instead rely on the callbacks as suggested below.
            Toast.makeText(this, "Rewarded Video is not ready", Toast.LENGTH_SHORT).show();
        }
    }
    void ＭopubShowInterstitial() {
        if (mInterstitial.isReady()) {
            mInterstitial.show();
        } else {
            // Caching is likely already in progress if `isReady()` is false.
            // Avoid calling `load()` here and instead rely on the callbacks as suggested below.
            Toast.makeText(this, "Ad is not ready", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInterstitialLoaded(MoPubInterstitial moPubInterstitial) {
        Toast.makeText(this, "Interstitial Ad is Loaded", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onInterstitialFailed(MoPubInterstitial moPubInterstitial, MoPubErrorCode moPubErrorCode) {
        Log.e(TAG, "error" + moPubErrorCode);
        Toast.makeText(this, "Ad is not ready", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInterstitialShown(MoPubInterstitial moPubInterstitial) {
        Toast.makeText(this, "The Ad is shown", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInterstitialClicked(MoPubInterstitial moPubInterstitial) {
        Toast.makeText(this, "The Ad is clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInterstitialDismissed(MoPubInterstitial moPubInterstitial) {
        Toast.makeText(this, "The Ad is dismissed", Toast.LENGTH_SHORT).show();
    }

    // Implement the IUnityAdsListener interface methods:
    private class UnityAdsListener implements IUnityAdsListener {
        @Override
        public void onUnityAdsReady(String surfacingId) {
            // Implement functionality for an ad being ready to show.
        }

        @Override
        public void onUnityAdsStart(String surfacingId) {
            // Implement functionality for a user starting to watch an ad.
        }

        @Override
        public void onUnityAdsFinish(String surfacingId, UnityAds.FinishState finishState) {
            // Implement functionality for a user finishing an ad.
        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
            // Implement functionality for a Unity Ads service error occurring.
        }
    }

    private String unityGameID = "4207728";
    private Boolean testMode = Boolean.FALSE;
    private String surfacingId = "Interstitial_Android";
    private String surfacingId2 = "Rewarded_Android";
    private static final String TAG = "Unity";
    public  int counter = 0;
    private FirebaseAnalytics mFirebaseAnalytics;


    public void DisplayInterstitialAd() {
        if (UnityAds.isReady(surfacingId)) {
            UnityAds.show(this, surfacingId);
        }
    }

    public void DisplayRewardedAd() {
        if (UnityAds.isReady(surfacingId2)) {
            UnityAds.show(this, surfacingId2);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // UnityAds.initialize(this, unityGameID, testMode);
        setContentView(R.layout.activity_apps_flyer_test);
        Map<String, String> mediatedNetworkConfiguration1 = new HashMap<>();
        mediatedNetworkConfiguration1.put("gameId", "4207728");
        SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder("6d883d577a9047faa14195ca4f1a3832")
               //.withMediationSettings()
             //   .withMediatedNetworkConfiguration(, mediatedNetworkConfiguration1)
           //    .withAdditionalNetworks(CustomAdapterConfiguration.class.getName())
           //    .withMediatedNetworkConfiguration(CustomAdapterConfiguration1.class.getName(), mediatedNetworkConfiguration)
           //    .withMediatedNetworkConfiguration(CustomAdapterConfiguration2.class.getName(), mediatedNetworkConfiguration)
           .withMediatedNetworkConfiguration(com.mopub.mobileads.UnityAdsAdapterConfiguration.class.getName(), mediatedNetworkConfiguration1)
                .withLogLevel(MoPubLog.LogLevel.DEBUG)
                .withLegitimateInterestAllowed(false)
                .build();
        MoPub.onCreate(this);

        mInterstitial = new MoPubInterstitial(this, "6d883d577a9047faa14195ca4f1a3832");
        mInterstitial.setInterstitialAdListener(this);
        UnityAds.setDebugMode(true);
      //  mInterstitial.load();
      //  MoPubRewardedAds.loadRewardedAd("40fd0bbc30e844e3b02cabffdc389385");
       // mInterstitial.load();
        // MoPub.initializeSdk();
        // Declare a new listener:
       // final UnityAdsListener myAdsListener = new UnityAdsListener();
        // Add the listener to the SDK:
       // UnityAds.addListener(myAdsListener);
        // Initialize the SDK:
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        TextView t2 = (TextView) findViewById(R.id.editText);
        t2.setText("Unity3d Awesome!");
        //Check preInstall button
        TextView dis = (TextView) findViewById(R.id.textView2);
        Button checkPreinstall = (Button) findViewById(R.id.preinstall);

        Button trackEventButton3 = findViewById(R.id.loadInterstitial);
        trackEventButton3.setEnabled(false);
        trackEventButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterstitial.load();
            }
        });
        Button trackEventButton4 = findViewById(R.id.loadvideo);
        trackEventButton4.setEnabled(false);
        trackEventButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoPubRewardedAds.loadRewardedAd("40fd0bbc30e844e3b02cabffdc389385");
            }
        });
        Button trackEventButton = findViewById(R.id.EventButton);
        trackEventButton.setEnabled(false);
        trackEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ＭopubShowInterstitial();
                //mInterstitial.show();
                // ＭopubShowInterstitial();
                // DisplayInterstitialAd();
            }
        });
        Button trackEventButton2 = findViewById(R.id.crosspromotion);
        trackEventButton2.setEnabled(false);
        trackEventButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ＭopubShowVideo();
                //   MoPubRewardedAds.showRewardedAd("40fd0bbc30e844e3b02cabffdc389385");
                //DisplayRewardedAd();
            }
        });
        TextView t3 = (TextView) findViewById(R.id.textView);
        t3.setText("Rewarded AD Displayed Counter:"+ counter);
        checkPreinstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoPub.initializeSdk(AppsFlyerTest.this, sdkConfiguration, initSdkListener());
                trackEventButton.setEnabled(true);
                trackEventButton2.setEnabled(true);
                trackEventButton3.setEnabled(true);
                trackEventButton4.setEnabled(true);
                rewardedAdListener = new MoPubRewardedAdListener() {
                    @Override
                    public void onRewardedAdCompleted(@NotNull Set<String> set, @NotNull MoPubReward moPubReward) {
                        counter = counter + 1;
                        t3.setText("Rewarded AD Ddisplaye Counter:"+ counter);
                    }

                    @Override
                    public void onRewardedAdLoadSuccess(String adUnitId) {
                        Toast.makeText(AppsFlyerTest.this, "Rewarded Video is Loaded", Toast.LENGTH_SHORT).show();
                        // Called when the ad for the given adUnitId has loaded. At this point you should be able to call MoPubRewardedAds.showRewardedAd() to show the ad.
                    }
                    @Override
                    public void onRewardedAdLoadFailure(String adUnitId, MoPubErrorCode errorCode) {
                        // Called when the ad fails to load for the given adUnitId. The provided error code will provide more insight into the reason for the failure to load.
                    }

                    @Override
                    public void onRewardedAdStarted(String adUnitId) {
                        // Called when a rewarded ad starts playing.
                    }

                    @Override
                    public void onRewardedAdShowError(String adUnitId, MoPubErrorCode errorCode) {
                        //  Called when there is an error while attempting to show the ad.
                    }

                    @Override
                    public void onRewardedAdClicked(@NonNull String adUnitId) {
                        //  Called when a rewarded ad is clicked.
                    }

                    @Override
                    public void onRewardedAdClosed(String adUnitId) {
                        // Called when a rewarded ad is closed. At this point your application should resume.
                    }
                };
                MoPubRewardedAds.setRewardedAdListener(rewardedAdListener);
                //  DisplayRewardedAd();
            }
        });


    }

    protected void onResume() {
        super.onResume();
    }

    private SdkInitializationListener initSdkListener() {
        return new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
           /* MoPub SDK initialized.
           Check if you should show the consent dialog here, and make your ad requests. */
            }
        };
    }
}


