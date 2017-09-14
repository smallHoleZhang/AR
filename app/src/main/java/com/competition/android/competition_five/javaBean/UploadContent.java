package com.competition.android.competition_five.javaBean;

import android.graphics.Bitmap;

/**
 * Created by Vincent on 2017/9/3.
 */

public class UploadContent {

    private String content;

    private String author;

    private String upload_time;

    private Bitmap mBitmap;

    public UploadContent() {
    }

    public UploadContent(String content, String author, String upload_time, Bitmap bitmap) {
        this.content = content;
        this.author = author;
        this.upload_time = upload_time;
        mBitmap = bitmap;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }
}
