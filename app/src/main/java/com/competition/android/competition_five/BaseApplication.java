package com.competition.android.competition_five;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.competition.android.competition_five.Entity.OcrList;
import com.competition.android.competition_five.Uilt.StaticUilt;

/**
 * Created by hasee on 2017/8/20.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AVObject.registerSubclass(OcrList.class);
        AVOSCloud.initialize(this, StaticUilt.LC_ID,StaticUilt.LC_AK);
    }

}
