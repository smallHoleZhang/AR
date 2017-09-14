package com.competition.android.competition_five.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.competition.android.competition_five.Adapter.ArAuthorAdapter;
import com.competition.android.competition_five.Adapter.Base.BaseAdapter;
import com.competition.android.competition_five.R;
import com.competition.android.competition_five.Uilt.OpenUile;
import com.competition.android.competition_five.Uilt.UploadDialog;
import com.competition.android.competition_five.activity.ar.ArItemShow;
import com.competition.android.competition_five.activity.ar.UploadShiPin;
import com.competition.android.competition_five.activity.ar.UploadTuPian;
import com.competition.android.competition_five.javaBean.UploadContent;
/*import com.hiar.sdk.UnityPlayerNativeActivity;*/

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/8/18.
 */

public class ArFragment extends Fragment implements View.OnClickListener{

    private RecyclerView mRecyclerView;

    private ArAuthorAdapter mAdapter;

    private List<UploadContent> mData;

    private Button btn_upload;

    private UploadDialog mDialog;

    private Intent mIntent;

    public static Fragment newInstance(){
        ArFragment arFragment = new ArFragment();
        return arFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ar_layout,container,false);
        OpenUile.setPrimaryDark(getActivity(),R.color.teal);

        initWidget(view);

        initRecycleView(view);

        return view;
    }

    private void initWidget(View view) {
        btn_upload = (Button) view.findViewById(R.id.upload_button);
        btn_upload.setOnClickListener(this);
    }

    private void initRecycleView(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.author_recycle);

        initData();

        mAdapter = new ArAuthorAdapter(getContext(),mData,R.layout.layout_ar_item);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                UploadContent uploadContent = mData.get(position);

                Intent intent = new Intent(getContext(), ArItemShow.class);
                intent.putExtra("title",uploadContent.getContent());
                intent.putExtra("author",uploadContent.getAuthor());
                startActivity(intent);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(manager);

    }

    private void initData() {

        mData = new ArrayList<>();

        Bitmap bitmap;

        for (int i=0;i<9;i++){

            UploadContent u = new UploadContent();

            if (i%3==0){

                u.setContent("android群英传");
            }else if (i%3==2){
                u.setContent("android开发艺术探索");
            }else {
                u.setContent("android第一行代码");
            }
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.add);
            u.setBitmap(bitmap);
            u.setUpload_time("2分钟前");
            u.setAuthor("Vincent");

            mData.add(u);

        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.upload_button:
                getDialog();
                break;
            case R.id.upload_photo:
                mIntent = new Intent(getContext(), com.hiar.sdk.UnityPlayerNativeActivity.class);
                startActivity(mIntent);
                mDialog.cancel_dialog();
                break;
            case R.id.upload_video:
                mIntent = new Intent(getContext(), UploadShiPin.class);
                startActivity(mIntent);
                mDialog.cancel_dialog();
                break;
            case R.id.upload_cancel_button:
                mDialog.cancel_dialog();
                break;

        }


    }

    private void getDialog() {

        mDialog = new UploadDialog(getContext());
        mDialog.show_dialog();

        RelativeLayout upload_photo = mDialog.getRelativeLayout(R.id.upload_photo);
        RelativeLayout upload_video = mDialog.getRelativeLayout(R.id.upload_video);
        Button btn_cancel = mDialog.getButton(R.id.upload_cancel_button);

        upload_photo.setOnClickListener(this);
        upload_video.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }
}