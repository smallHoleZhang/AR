package com.competition.android.competition_five.javaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/7/17.
 */

public class TuPian {

    private static TuPian mTuPian = new TuPian();

    private int checkBoxVisible;

    private String imagePath;

    private List<TuPian> mList;

    private TuPian(){
        if (mList==null){
            mList = new ArrayList<>();
        }
    }

    public static TuPian newInstance(){
        return mTuPian;
    }

    public void addData(int checkBoxVisible,String imagePath){
        TuPian tuPian = new TuPian();
        tuPian.setCheckBoxVisible(checkBoxVisible);
        tuPian.setImagePath(imagePath);
        mList.add(tuPian);
    }

    public void deleteData(TuPian tuPian){
        mList.remove(tuPian);
    }

    public int getDataCount(){
        return mList.size();
    }

    public List<TuPian> getDatas(){
        return mList;
    }

    public TuPian getData(int position){

        return mList.get(position);

    }

    public void clear(){
        mList.clear();
    }

    public int getCheckBoxVisible() {
        return checkBoxVisible;
    }

    public void setCheckBoxVisible(int checkBoxVisible) {
        this.checkBoxVisible = checkBoxVisible;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
