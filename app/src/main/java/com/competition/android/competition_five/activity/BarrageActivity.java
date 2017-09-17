package com.competition.android.competition_five.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.baidu.ocr.ui.util.StaticParam;
import com.competition.android.competition_five.Entity.Barrage;
import com.competition.android.competition_five.R;
import com.competition.android.competition_five.Uilt.L;
import com.competition.android.competition_five.Uilt.Semblance;
import com.competition.android.competition_five.Uilt.StaticUilt;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * G -- 弹幕
 */

public class BarrageActivity extends AppCompatActivity {

    private ImageView photo_image;

    private DanmakuView barrage;

    private Button send_msg;

    private EditText barrage_msg;

    private DanmakuContext danmakuContext;
    private boolean showDanmaku;

    private Barrage barrageBean;

    private String ocr_content;//存放获取到的文字

    private static final String TAG = "BarrageActivity";

    private String similar_result;

    private List<String> barrages;//从服务器中获取到的弹幕

    private OkHttpClient mClient;
    public volatile static String mAccess_token;


    private static final int BARRAGE_START = 1;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (msg.arg1==BARRAGE_START){
                for (final String s : barrages) {
                    addDanmaku(s,false);

                }
            }

        }
    };

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrage);

        Log.d(TAG, "onCreate: ---来了");

        barrages = new ArrayList<>();

        mClient = new OkHttpClient();

        ocr_content = getIntent().getStringExtra("ocr_content");

        Log.d(TAG, "onCreate: "+ocr_content);
        
        photo_image = (ImageView) this.findViewById(R.id.image_dialog);

        barrage = (DanmakuView) this.findViewById(R.id.danmaku_view);

        send_msg = (Button) this.findViewById(R.id.send);

        barrage_msg = (EditText) this.findViewById(R.id.edit_text);

        photo_image.setImageBitmap(StaticParam.BITMAP_PHOTO);

        send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = barrage_msg.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    if (barrageBean==null) {
                     barrageBean =new Barrage();
                    }

                    barrageBean.addParam(ocr_content,content);

                    addDanmaku(content, true);
                    barrage_msg.setText("");
                }
            }
        });

        barrage.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
                barrage.start();
                getBarrages();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });

        danmakuContext = DanmakuContext.create();
        barrage.prepare(parser, danmakuContext);

    }

    /**
     * 从服务器中获取弹幕信息
     */
    private void getBarrages() {

        AVQuery<AVObject> query = new AVQuery<>("BarrageTable");
        query.selectKeys(Arrays.asList("label"));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {

                List<String> labels = null;

                for (AVObject avObject : list) {

                    if (labels==null){

                        labels = new ArrayList<String>();

                    }

                    String label = avObject.get("label").toString();

                    labels.add(label);
                    Log.d(TAG, "done: label="+label);
                }

                getBarrageFromLabel(labels);
            }
        });
    }

    /**
     * 根据图片中的内容来获取弹幕
     * @param labels
     */
    private void getBarrageFromLabel(List<String> labels) {

        Semblance semblance = new Semblance();

        for (final String label : labels) {

            Log.d(TAG, "getBarrageFromLabel: labels----"+ocr_content);

            semblance.getSemblance(label,ocr_content);



            FormBody body = new FormBody.Builder()
                    .add("grant_type", "client_credentials")
                    .add("client_id", StaticUilt.NLP_AK)
                    .add("client_secret", StaticUilt.NLP_SK)
                    .build();


            Request request = new Request.Builder()
                    .url("https://aip.baidubce.com/oauth/2.0/token")
                    .post(body)
                    .build();

            mClient.newCall(request).enqueue(new Callback() {
                String token;
                @Override
                public void onFailure(Call call, IOException e) {
                    L.d(e.getMessage());
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    if (response.isSuccessful()) {



                        String json = response.body().string();

                        try {
                            JSONObject jsonObject = new JSONObject(json);



                            mAccess_token = jsonObject.getString("access_token");


                            JSONObject jsonObject1 = new JSONObject();

                            try {
                                jsonObject1.put("text_1", label);
                                jsonObject1.put("text_2", ocr_content);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            L.d("getSampleScore: object = " + jsonObject1.toString());

                            RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=gbk"), jsonObject1.toString());


                            L.d( "getSemblance: " + mAccess_token);
                            Request request = new Request.Builder()
                                    .url("https://aip.baidubce.com/rpc/2.0/nlp/v2/simnet?access_token="+ mAccess_token + "&Content-Type=application/json")
                                    .post(body)
                                    .build();



                            mClient.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                    L.d(e.getMessage());

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {

                                    try {
                                        Thread.currentThread().sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    double similar ;

                                    byte[] responseBytes = response.body().bytes();

                                    String json = new String(responseBytes, "GBK");

                                    L.d("onResponse: json=" + json);

                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = new JSONObject(json);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (response.isSuccessful()) {

                                        try {
                                            L.d("success resopnse = " + jsonObject.get("score"));

                                            similar = Double.valueOf(jsonObject.get("score").toString());
                                            if (similar>0.7){

                                                AVQuery<AVObject> query = new AVQuery<>("BarrageTable");

                                                Log.d(TAG, "getBarrageFromLabel: label = "+label);

                                                query.whereEqualTo("label",label);

                                                query.findInBackground(new FindCallback<AVObject>() {
                                                    @Override
                                                    public void done(List<AVObject> list, AVException e) {

                                                        for (AVObject avObject : list) {

                                                            List<String> barragesFromService= (List<String>) avObject.get("barrage_list");

                                                            for (String s : barragesFromService) {

                                                                Log.d(TAG, "done: barrage = "+s);

                                                                barrages.add(s);

                                                            }
                                                            Log.d(TAG, "done: 00000."+barrages);

                                                        }

                                                        Message msg = new Message();
                                                        msg.arg1 = BARRAGE_START;
                                                        mHandler.sendMessage(msg);

                                                    }
                                                });

                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    } else {

                                        try {
                                            L.d("onResponse:wandan" + jsonObject.get("log_id"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            });



                        } catch (Exception e) {

                            e.printStackTrace();
                        }


                    }

                }

            });
        }

    }


    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    /**
     * 随机生成一些弹幕内容以供测试
     */



    /**
     * 向弹幕View中添加一条弹幕
     * @param content
     *          弹幕的具体内容
     * @param  withBorder
     *          弹幕是否有边框
     */
    private void addDanmaku(String content, boolean withBorder) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(barrage.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        barrage.addDanmaku(danmaku);
    }

    /**
     * sp转px的方法。
     */
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void finish() {
        super.finish();
        barrages = null;
        barrageBean = null;
    }
}
