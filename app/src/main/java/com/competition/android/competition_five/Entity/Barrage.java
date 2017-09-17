package com.competition.android.competition_five.Entity;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.competition.android.competition_five.tables.BarrageTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vincent on 2017/9/14.
 */

public class Barrage extends AVObject{

    private Map<String ,List<String> > barrages;

    private static final String TAG = "Barrage";

    private AVObject mAVObject ;

    public Barrage() {

        barrages = new HashMap<>();

        mAVObject = new AVObject("BarrageTable");
    }

    public Map<String, List<String>> getBarrages() {
        return barrages;
    }

    public void setBarrages(Map<String, List<String>> barrages) {
        this.barrages = barrages;
    }

    public void addParam(String label,String barrage){

        if (!barrages.containsKey(label)){
            barrages.put(label,new ArrayList<String>());
            mAVObject.put("label",label);
        }

        List<String> contents = barrages.get(label);

        contents.add(barrage);

        mAVObject.put("barrage_list",contents);


        Log.d(TAG, "addParam: barrage = "+barrages);

        mAVObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e==null){
                    Log.d(TAG, "done: 数据保存成功!");
                }else {
                    e.printStackTrace();
                }
            }
        });
    }
}
