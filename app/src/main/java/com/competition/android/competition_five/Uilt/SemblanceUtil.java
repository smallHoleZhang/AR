package com.competition.android.competition_five.Uilt;

import android.util.Log;

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

public class SemblanceUtil {

    private OkHttpClient mClient;

    private String mAccess_token;

    private boolean isTokenGet = false;

    private String result = "0";

    public SemblanceUtil(){

        mClient = new OkHttpClient();
        if (!isTokenGet) {
            getAccess_token();//获取access_token
        }
    }



    public void getAccess_token() {
        FormBody body = new FormBody.Builder()
                .add("grant_type","client_credentials")
                .add("client_id",StaticUilt.NLP_AK)
                .add("client_secret",StaticUilt.NLP_SK)
                .build();




        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .post(body)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.d(e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {



                if (response.isSuccessful()){


                    String json = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(json);

                        mAccess_token = jsonObject.getString("access_token");
                    }catch (Exception e){

                        e.printStackTrace();
                    }

                    L.d("onResponse: token = "+mAccess_token);

                    isTokenGet = true;

                }

            }
        });
    }

    public  String getSemblance(String value1,String value2){


        if (isTokenGet){

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("text_1",value1);
                jsonObject.put("text_2",value2);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            L.d("getSampleScore: object = "+jsonObject.toString());

            RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=gbk"),jsonObject.toString());

            Request request = new Request.Builder()
                    .url("https://aip.baidubce.com/rpc/2.0/nlp/v2/simnet?access_token="+mAccess_token+"&Content-Type=application/json")
                    .post(body)
                    .build();

            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    L.d(e.getMessage());

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {


                    byte[] responseBytes=response.body().bytes();

                    String json = new String(responseBytes,"GBK");

                    L.d("onResponse: json="+json);

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(json);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (response.isSuccessful()){

                        try {
                            L.d("success resopnse = "+jsonObject.get("score"));

                            result= jsonObject.get("score").toString();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else {

                        try {
                            L.d("onResponse:wandan"+jsonObject.get("log_id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }else{

            L.d("getSampleScore: null");
        }

        return result;

    }
}
