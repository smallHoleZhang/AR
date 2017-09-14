package com.competition.android.competition_five.Entity;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hasee on 2017/8/20.
 */
@AVClassName("OcrList")
public class OcrList extends AVObject implements Serializable {
    private String id;

    public String getId() {
        return getString("id") ;
    }

    public void setId(String id) {
       put("id",id);
    }

    private String list_name;
    public String getList_name() {
        return getString("list_name");
    }

    public void setList_name(String list_name) {
        put("list_name",list_name);
    }
    private ArrayList <String>  mArrayList = new ArrayList<>();

    public List<String> getArrayList() {
        return getList("mArrayList") ;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        put("mArrayList",arrayList);
    }
    public void AddArraylist(String list_name)
    {
        mArrayList.add(list_name);
        setArrayList(mArrayList);
    }

    public String userId;

    public String getUserId() {
        String string = getAVObject("userId").getObjectId();

        return string;
    }

    public void setUserId(String userId) {
        put("userId", AVObject.createWithoutData("_User", userId));
    }
}
