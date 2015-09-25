package com.kaolafm.kaolaautoupdater.util;

import android.util.Log;

import com.kaolafm.kaolaautoupdater.BuildConfig;

/**
 * Created by V on 2015/9/23.
 */
public class Logger {
    private static final boolean DEBUG;

    static {
//        DEBUG = true;
        DEBUG = BuildConfig.DEBUG;
    }

    private static final String TAG = "kaola.updater";

    public static void i(String s) {
        if (DEBUG)
            Log.i(TAG, s);
    }

    public static void d(String s) {
        if (DEBUG)
            Log.d(TAG, s);
    }

    public static void e(String s) {
        if (DEBUG)
            Log.e(TAG, s);
    }

    public static void v(String tag, String s) {
        if (DEBUG)
            Log.v(tag, s);
    }

    public static void v(String s) {
        if (DEBUG)
            Log.v(TAG, s);
    }

    public static void i(String tag, String s) {
        if (DEBUG)
            Log.i(tag, s);
    }

    public static void d(String tag, String s) {
        if (DEBUG)
            Log.d(tag, s);
    }

    public static void e(String tag, String s) {
        if (DEBUG)
            Log.e(tag, s);
    }
}
