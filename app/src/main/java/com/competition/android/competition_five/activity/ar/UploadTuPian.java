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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.competition.android.competition_five.Adapter.Base.BaseAdapter;
import com.competition.android.competition_five.Adapter.TuPianAdapter;
import com.competition.android.competition_five.R;
import com.competition.android.competition_five.Uilt.GetPhotoFromAlbum;
import com.competition.android.competition_five.Uilt.PhotoDialog;
import com.competition.android.competition_five.javaBean.TuPian;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/7/16.
 */

public class UploadTuPian extends AppCompatActivity implements View.OnClickListener, BaseAdapter.OnItemClickListener {

    private ImageButton mBack;
    private TextView mEdit;
    private ImageView mPhoto;
    private RecyclerView mRecyclerView;
    private TuPianAdapter mTuPianAdapter;
    private List<TuPian> datas;
    private PhotoDialog mDialog;

    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static int PHOTO_STATE = 1;//拍照为1，从相册获取为2
    private GetPhotoFromAlbum mGetPhoto;
    private View mView;
    private TuPian mTuPian;
    private int mPosition = -1;
    private boolean isFirst = true;

    private View itemView;
    private CheckBox mCheckBox;

    private boolean isLogo = false;

    private static final String TAG = "UploadTuPian";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_upload_tupian);
        mGetPhoto = new GetPhotoFromAlbum(this);
        initWidget();
    }

    private void initWidget() {

        mBack = (ImageButton) this.findViewById(R.id.back_btn);
        mBack.setOnClickListener(this);
        mEdit = (TextView) this.findViewById(R.id.toolbar_edit);
        mEdit.setText("完成");
        mEdit.setOnClickListener(this);
        mPhoto = (ImageView) this.findViewById(R.id.upload_tupian_photo);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_photo_tag);
        mPhoto.setImageBitmap(bitmap);
        mPhoto.setOnClickListener(this);


        initData();

        mRecyclerView = (RecyclerView) this.findViewById(R.id.upload_tupian_recycle_view);

        if (mTuPianAdapter==null) {
            initRecycleView();
        }

    }

    private void initRecycleView() {

        mTuPianAdapter = new TuPianAdapter(this, datas, R.layout.layout_tupian_recycle_item);

        mTuPianAdapter.setOnItemClickListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mTuPianAdapter);

    }


    /**
     * 填充datas
     */
    private void initData() {

        if (datas==null) {
            mTuPian = TuPian.newInstance();
            datas = mTuPian.getDatas();
            refresh();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mTuPian==null){
            mTuPian =TuPian.newInstance();
        }
        if (datas==null){
            datas = new ArrayList<>();
            refresh();
            datas = mTuPian.getDatas();

        }else {

            if (!isLogo) {
                refresh();
                if (mPosition != -1) {
                    TuPian tupian = mTuPian.getData(mPosition);
                    tupian.setCheckBoxVisible(1);
                    tupian.setImagePath(GetPhotoFromAlbum.mImagePath);
                    datas = mTuPian.getDatas();
                }
            }else {
                isLogo = false;
            }
        }
        initRecycleView();

        if (itemView!=null){
            Log.i(TAG, "onRestart: "+"itemView!=null");


        }
    }

    /**
     * 刷新数据
     */
    private void refresh() {

        if (datas.size() < 9) {
            mTuPian.addData(0,null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTuPian.clear();
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
            case R.id.upload_tupian_photo:
                isLogo=true;
                mView = mPhoto;
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
            case R.id.upload_tupian_checkBox:
                deleteItem();
                break;
        }
    }


    /**
     * 删除当前点击的item
     */
    public void deleteItem() {

        if (datas.size()>1){
            mTuPian.deleteData(mTuPian.getData(mPosition));
            mTuPianAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 从相册选择图片功能的实现
     */
    private void choosePhoto() {

        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        intent.putExtra("position",mPosition);
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
                takePhoto();
            } else if (PHOTO_STATE == 2) {
                choosePhoto();
            }
        }

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
                        ((ImageView)mView).setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        mGetPhoto.handleImageOnKitKat(mView, data);
                    } else {
                        mGetPhoto.handleImageBeforeKitKat(mView, data);
                    }
                    mDialog.cancel_dialog();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void OnClick(View view, int position) {

        ImageView image = (ImageView) view.findViewById(R.id.upload_tupian_imageView);
        itemView = view;
        mView = image;
        mPosition = position;
        show_dialog();
        mCheckBox = (CheckBox) view.findViewById(R.id.upload_tupian_checkBox);
        mCheckBox.setOnClickListener(this);
    }

}