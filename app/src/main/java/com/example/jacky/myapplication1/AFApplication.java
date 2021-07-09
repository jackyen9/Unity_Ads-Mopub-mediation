package com.example.jacky.myapplication1;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.os.StrictMode;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.appsflyer.share.ShareInviteHelper;
import com.appsflyer.share.LinkGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.deeplink.DeepLink;
import com.appsflyer.deeplink.DeepLinkListener;
import com.appsflyer.deeplink.DeepLinkResult;


public class AFApplication extends Application {


    private static final String AF_DEV_KEY = "T3fLqSXq83YNHXUCBKULja";
    private static final String TAG = "AppsFlyer";

    private String data;
    //final RxPermissions rxPermissions = new RxPermissions(this)

    //AppsFlyerLib.LOG_TAG
    @Override
    public void onCreate(){
       /* Strictmode Test
       StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectNonSdkApiUsage()
                .penaltyLog()
                .build());*/
        super.onCreate();

        //com.appsflyer.AppsFlyerLibCore.

       /*
       //AppsFLyer 4.11 and below conversionDataListener
       AppsFlyerConversionListener conversionDataListener =
                new AppsFlyerConversionListener() {
                   @Override
                    public void onInstallConversionDataLoaded(Map<String, String> conversionData) {
                        Log.d(TAG, "onInstallConversionDataLoaded");
                        if (conversionData != null) {
                            Log.d(TAG, "see here"+conversionData.toString());
                            Log.d(TAG, conversionData.keySet().toString());
                            Log.d(TAG, conversionData.values().toString());
                           // log.d(TAG, Device.getAndroid())
                            data = conversionData.toString();
                        }
                        //setInstallData(conversionData);
                    }
                    @Override
                    public void onInstallConversionFailure(String errorMessage) {
                        if (errorMessage == null) {
                            Log.d(TAG, "null");
                        } else {
                            Log.d(TAG, errorMessage);
                        }
                    }
                    public String getData() {
                        return data;
                    }
                    @Override
                    public void onAppOpenAttribution(Map<String, String> attributionData) {
                        Log.d(TAG, "onAppOpenAttribution");
                        if (attributionData != null) {
                            Log.d(TAG, attributionData.toString());
                            Log.d(TAG, attributionData.keySet().toString());
                            Log.d(TAG, attributionData.values().toString());
                        }
                    }
                    @Override
                    public void onAttributionFailure(String errorMessage) {
                        if (errorMessage == null) {
                            Log.d(TAG, "null");
                        } else {
                            Log.d(TAG, errorMessage);
                        }
                    }
                };*/


       AppsFlyerLib.getInstance().subscribeForDeepLink(new DeepLinkListener(){
           @Override
           public void onDeepLinking( DeepLinkResult deepLinkResult) {
               DeepLinkResult.Error error = deepLinkResult.getError();
               if (error != null) {
                   // You can add here error handling code
                   Log.d(TAG, "There was an error getting Deep Link data");
                   return;
               }
               DeepLink deepLinkObj = deepLinkResult.getDeepLink();
               try {
                   Log.d(TAG, "The Unified DeepLink data is: " + deepLinkObj.toString());
               } catch (Exception e) {
                   Log.d(TAG, "DeepLink data came back null");
                   return;
               }
               // An example for using is_deferred
               if (deepLinkObj.isDeferred()) {
                   Log.d(TAG, "This is a deferred deep link");
               } else {
                   Log.d(TAG, "This is a direct deep link");
               }
               // An example for using a generic getter
               String fruitName = "";
               try {
                   fruitName = deepLinkObj.getStringValue("deep_link_value");
                   Log.d(TAG, "The DeepLink will route to: " + fruitName);
               } catch (Exception e) {
                   Log.d(TAG, "Custom param fruit_name was not found in DeepLink data");
                   return;
               }
             //  goToFruit(fruitName, deepLinkObj);
           }
       });
        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {

            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {
                Log.d("AppsFlyer", " FULL GCD" + conversionData);
                for (String attrName : conversionData.keySet()) {
                    Log.d("AppsFlyer", "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
                //Map<String,Object> map = new HashMap<String,Object>(); //Object is containing String
                Map<String,String> newMap =new HashMap<String,String>();
                for (Map.Entry<String, Object> entry : conversionData.entrySet()) {
                    if(entry.getValue() instanceof String){
                        newMap.put(entry.getKey(), (String) entry.getValue());
                    }
                }
             // onAppOpenAttribution(newMap);
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d("AppsFlyer", "error getting conversion data: " + errorMessage);
            }

           @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {
               Log.d("AppsFlyer", "Onapp Open FULL OAOA" + conversionData);
              for (String attrName : conversionData.keySet()) {
                   Log.d("AppsFlyer", "attribute: " + attrName + " = " + conversionData.get(attrName));
               }

            }

            @Override
            public void onAttributionFailure(String errorMessage) {
               Log.d("AppsFlyer", "error onAttributionFailure : " + errorMessage);
           }
        };

        String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
       // String senderId = "186869854051"; /* A.K.A Project Number */
      //  AppsFlyerLib.getInstance().enableUninstallTracking(senderId); /* ADD THIS LINE HERE */
        AppsFlyerLib.getInstance().setMinTimeBetweenSessions(1);
        AppsFlyerLib.getInstance().addPushNotificationDeepLinkPath("engagement","esp_link");
        //AppsFlyerLib.getInstance().registerConversionListener();
        //AppsFlyerLibCore.LOG_TAG
       //AppsFlyerLib.getInstance().setAndroidIdData(id);
       //AppsFlyerLib.getInstance().set(AppsFlyerProperties.CHANNEL, "oppostore_int");
       AppsFlyerLib.getInstance().setOutOfStore("oppo_int");
        //AppsFlyerLib.getInstance().setCollectAndroidID(true);
        //AppsFlyerLib.getInstance().setCollectOaid(true);
        //AppsFlyerLib.getInstance().setCollectIMEI(false);
        AppsFlyerLib.getInstance().setCollectIMEI(true);
        //AppsFlyerLib.getInstance().
       //AppsFlyerLib.getInstance().setLogLevel(com.appsflyer.AFLogger.LogLevel.);
       // AppsFlyerLib.getInstance().setDebugLog(true);
    //    AppsFlyerLib.getInstance().
        AppsFlyerLib.getInstance().setAppInviteOneLink("1aJn");

        //String Attribution_ID = AppsFlyerLib.getInstance().;
       // Log.d(TAG, "Attribution ID"+Attribution_ID);
        //AppsFlyerLib.getInstance().setPreinstallAttribution("xiaomi_int", "preload","");
       // AppsFlyerLib.getInstance().waitForCustomerUserId(true);
      // AppsFlyerLib.getInstance().init("T3fLqSXq83YNHXUCBKULja", conversionListener, getApplicationContext());
       // AppsFlyerLib.getInstance().init("T3fLqSXq83YNHXUCBKULja",null,  getApplicationContext());
      // AppsFlyerLib.getInstance().start(this);
       // AppsFlyerLib.getInstance().setOaidData("123456");
        //AppsFlyerLib.getInstance().reportTrackSession(getApplicationContext());
        //AppsFlyerLib.getInstance().setCustomerUserId("test");
        //public static String getAfDevKey(){return AF_DEV_KEY;}
        //Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
        //Log.d(TAG, "conversion data check"+ data);

        //AppsFlyerLib.getInstance().setCustomerUserId("test");

    }
   // public void onResume(){ }

}
