package com.competition.android.competition_five.javaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/7/22.
 */

public class ShiPin {

    private static ShiPin mShiPin = new ShiPin();

    private List<ShiPin> mList;

    private String videoPath;

    private String name;

    private String timeLong;

    private String singer;


    private ShiPin(){

        if (mList==null){
            mList = new ArrayList<>();
        }

    }

    public void addData(String videoPath,String name,String timeLong,String singer){
        ShiPin shiPin = new ShiPin();

        shiPin.setVideoPath(videoPath);
        shiPin.setName(name);
        shiPin.setTimeLong(timeLong);
        shiPin.setSinger(singer);

        mList.add(shiPin);
    }

    public void deleteData(ShiPin shiPin){
        mList.remove(shiPin);
    }

    public int getDataCount(){
        return mList.size();
    }

    public List<ShiPin> getDatas(){
        return mList;
    }

    public ShiPin getData(int position){

        return mList.get(position);

    }

    public void clear(){
        mList.clear();
    }

    public static ShiPin newInstance(){
        return mShiPin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    private String mediaType;

    private String filesize;

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
