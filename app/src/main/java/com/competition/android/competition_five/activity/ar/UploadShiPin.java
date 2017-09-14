package com.competition.android.competition_five.activity.ar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.competition.android.competition_five.R;
import com.competition.android.competition_five.Uilt.GetPhotoFromAlbum;
import com.competition.android.competition_five.Uilt.GetVideoFromLocal;
import com.competition.android.competition_five.Uilt.PhotoDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/7/16.
 */

public class UploadShiPin extends AppCompatActivity implements View.OnClickListener {


    public UploadShiPin() {
    }

    private ImageButton mBack;
    private TextView mEdit;
    private ImageView mPhoto;
    private ImageView mVideo;
    private PhotoDialog mDialog;

    private View mView;

    private Uri imageUri;
    private Uri videoUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int TAKE_VIDEO = 3;
    public static final int CHOOSE_VIDEO = 4;
    public static int PHOTO_STATE = 1;//拍照为1，从相册获取为2

    public static int PHOTO_OR_VIDEO = -1;
    private GetPhotoFromAlbum mGetPhoto;
    private GetVideoFromLocal mGetVideo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_upload_shipin);

        mGetPhoto = new GetPhotoFromAlbum(this);
        mGetVideo = new GetVideoFromLocal(this);

        initWidget();
    }

    private void initWidget() {

        mBack = (ImageButton) this.findViewById(R.id.back_btn);
        mBack.setOnClickListener(this);
        mEdit = (TextView) this.findViewById(R.id.toolbar_edit);
        mEdit.setText("完成");
        mEdit.setOnClickListener(this);

        mPhoto = (ImageView) this.findViewById(R.id.upload_shipin_photo);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_photo_tag);
        mPhoto.setImageBitmap(bitmap);
        mPhoto.setOnClickListener(this);

        mVideo = (ImageView) this.findViewById(R.id.upload_shipin_video);
        mVideo.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_video_tag));
        mVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.toolbar_edit:
                operate();
                break;
            case R.id.upload_shipin_photo:
                PHOTO_OR_VIDEO = 1;
                mView = mPhoto;
                show_dialog();
                break;
            case R.id.upload_shipin_video:
                PHOTO_OR_VIDEO = 2;
                mView = mVideo;
                show_dialog();
                break;
            case R.id.takePhoto:
                PHOTO_STATE = 1;
                checkPermission();
                break;
            case R.id.choosePhoto:
                PHOTO_STATE = 2;
                checkPermission();
                break;
            case R.id.photo_cancel:
                if (mDialog != null) {
                    mDialog.cancel_dialog();
                }
                break;
        }
    }

    /**
     * 动态申请权限
     */

    private void checkPermission() {

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            if (PHOTO_STATE == 1) {
                if (PHOTO_OR_VIDEO == 1) {
                    takePhoto();
                } else {
                    takeVideo();
                }
            } else if (PHOTO_STATE == 2) {
                if (PHOTO_OR_VIDEO == 1) {
                    choosePhoto();
                } else {
                    chooseVideo();
                }
            }
        }

    }

    /**
     * 获取本地视频
     */

    private void chooseVideo() {

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHOOSE_VIDEO);
    }

    /**
     * 拍摄视频
     */

    private void takeVideo() {

        Intent intent = new Intent();
        intent.setAction("android.media.action.VIDEO_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");

        // 保存录像到指定的路径
        File outputVideo = new File("/sdcard/video.3pg");

        try {
            if (outputVideo.exists()) {
                outputVideo.delete();
            }
            outputVideo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        videoUri = Uri.fromFile(outputVideo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        startActivityForResult(intent, TAKE_VIDEO);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    if (PHOTO_STATE == 1) {
                        takePhoto();
                    } else if (PHOTO_STATE == 2) {
                        choosePhoto();
                    }
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    /**
     * 从相册选择图片功能的实现
     */
    private void choosePhoto() {

        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);//打开相册

    }

    /**
     * 拍照功能的实现
     */
    private void takePhoto() {

        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(this, "com.File_Provider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }

        //启动相机

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);

    }

    /**
     * 显示Dialog选择
     */
    private void show_dialog() {
        mDialog = new PhotoDialog(this);
        mDialog.show_dialog();

        TextView takePhoto = mDialog.getTextView(R.id.takePhoto);
        TextView chosePhoto = mDialog.getTextView(R.id.choosePhoto);
        TextView cancel = mDialog.getTextView(R.id.photo_cancel);

        takePhoto.setOnClickListener(this);
        chosePhoto.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void operate() {


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        mPhoto.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        mGetPhoto.handleImageOnKitKat(mView, data);
                        mDialog.cancel_dialog();
                    } else {
                        mGetPhoto.handleImageBeforeKitKat(mView, data);
                        mDialog.cancel_dialog();
                    }
                }
                break;
            case TAKE_VIDEO:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = GetVideoFromLocal.getVideoThumbnail(videoUri.toString());
                    mVideo.setImageBitmap(bitmap);
                }
                break;
            case CHOOSE_VIDEO:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String path = GetVideoFromLocal.getPath(this,uri);
                    Bitmap bitmap = GetVideoFromLocal.getLocalVideoThumbnail(path);
                    mVideo.setImageBitmap(bitmap);
                    mDialog.cancel_dialog();
                }
                break;
            default:
                break;
        }
    }

}
