package com.wherego.delivery.driver;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.wherego.delivery.driver.helpers.SharedHelper;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;


/**
 * Created by Amit Tiwari on 2/01/20.
 */

public class App extends Application {

    public static final String TAG = App.class
            .getSimpleName();
    private static App mInstance;
    private static Context context;
    private RequestQueue mRequestQueue;

    public static Context getContext() {
        return context;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public static String trimMessage(String json) {
        String trimmedString = "";

        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> iter = jsonObject.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    JSONArray value = jsonObject.getJSONArray(key);
                    for (int i = 0, size = value.length(); i < size; i++) {
                        Log.e("Errors in Form", "" + value.getString(i));
                        trimmedString += value.getString(i);
                        if (i < size - 1) {
                            trimmedString += '\n';
                        }
                    }
                } catch (JSONException e) {

                    trimmedString += jsonObject.optString(key);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        Log.e("Trimmed", "" + trimmedString);

        return trimmedString;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        setLocale(SharedHelper.getKey(this, "selectedlanguage"));

        EmojiManager.install(new IosEmojiProvider());

        context = getApplicationContext();

        Fabric fabric = new Fabric.Builder(App.this)
                .kits(new Crashlytics())
                .debuggable(BuildConfig.DEBUG)
                .build();

        Fabric.with(fabric);

        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this);

    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
           // mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            mRequestQueue = Volley.newRequestQueue(this, new OkHttpStack());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the no_user tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
