package com.competition.android.competition_five.Uilt;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.competition.android.competition_five.R;
import com.competition.android.competition_five.javaBean.TuPian;


/**
 * Created by lx on 2017/7/19.
 */


/**
 * 获取本地图片并设置其缩略图
 */
public class GetPhotoFromAlbum {

    private Context mContext;

    private TuPian mTuPian = TuPian.newInstance();

    private int mPosition = -1;
    public static String mImagePath = null;

    public GetPhotoFromAlbum(Context context) {
        mContext = context;
    }

    /**
     * 根据得到的uri来将图片设置到对应的imageView上
     * @param view
     * @param imagePath
     */
    public void diaplayImage(View view, String imagePath) {


        if (imagePath != null) {
            if (view.getId()== R.id.upload_tupian_imageView) {
                mImagePath = imagePath;
            }else {
                ((ImageView)view).setImageBitmap(BitmapFactory.decodeFile(imagePath));
            }
        } else {
            Toast.makeText(mContext, "获取图片异常!", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 获取图片真正的uri
     * @param uri
     * @param selection
     * @return
     */
    public String getImagePath(Uri uri, String selection) {

        String path = null;
        //通过uri和selection来获取真实的图片路径
        Cursor cursor = mContext.getContentResolver().query(uri, null, selection, null, null);

        if(cursor!=null) {

            if (cursor.moveToFirst()) {
                do {
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }else {
            Toast.makeText(mContext, "没有找到本地图片", Toast.LENGTH_SHORT).show();
        }
        return path;

    }
    /**
     * Android4.4以下的系统使用此方法来处理图片
     *
     * @param data
     */
    public void handleImageBeforeKitKat(View view,Intent data) {
        Uri uri = data.getData();
        mPosition = data.getIntExtra("position",-1);
        String imagePath = getImagePath(uri, null);
        diaplayImage(view,imagePath);
    }

    /**
     * Android4.4以上的系统使用此方法来处理图片
     *
     * @param data
     */
    @TargetApi(19)
    public void handleImageOnKitKat(View view, Intent data) {

        String imagePath = null;
        mPosition = data.getIntExtra("position",-1);
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(mContext, uri)) {
            //如果是Document类型的uri,则通过Document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                //如果是content类型的Uri,则使用普通方法处理
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                //如果是file类型的uri,则直接获取图片路径即可
                imagePath = uri.getPath();
            }
            diaplayImage(view,imagePath);
        }

    }

}
