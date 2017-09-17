package com.competition.android.competition_five.Uilt;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.competition.android.competition_five.activity.BarrageActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Vincent on 2017/8/23.
 */

public class Semblance {

    private OkHttpClient mClient;

    public volatile static String mAccess_token;

    private boolean isTokenGet = false;


    private Context mContext;

    private String result = "0";

    public Semblance() {

        mClient = new OkHttpClient();

    }

    public String getSemblance(final String value1,final String value2) {


        L.d( "result =  "+result);
        return result;

    }

    public void setAccess_token(String access_token) {
        mAccess_token = access_token;
    }

}
