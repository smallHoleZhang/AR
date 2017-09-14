package com.competition.android.competition_five.Uilt;

import android.util.Log;

/**
 * Created by hasee on 2017/3/10.
 */

public class L {

    //开关
    public static    boolean DEBUG = true;
    //TAG
    public  static  final  String TAG = "NUC";

    public static void d(String text)
    {
        if(DEBUG)
        {
            Log.d(TAG, text);
        }
    }
    public static void w(String text)
    {
        if(DEBUG)
        {
            Log.d(TAG, text);
        }
    }

    public static void i(String text)
    {
        if(DEBUG)
        {
            Log.d(TAG, text);
        }
    }
    public static void NO()
    {
        DEBUG = false;
    }
    public static void YES()
    {
        DEBUG = true;
    }
}
