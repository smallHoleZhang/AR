package com.competition.android.competition_five.UseActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.competition.android.competition_five.Adapter.NodecontextRecycleViewAdapter;
import com.competition.android.competition_five.Adapter.OcrRecycViewAdapter;
import com.competition.android.competition_five.Entity.OcrList;
import com.competition.android.competition_five.Fragment.NodeFragment;
import com.competition.android.competition_five.R;
import com.competition.android.competition_five.Uilt.L;

import java.util.ArrayList;

/**
 * Created by hasee on 2017/8/20.
 */

public class NodeContextActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodecontext_layout);
        OcrList ocrList = NodeFragment.user_0crList;
        ArrayList<String> arrayList = (ArrayList<String>) ocrList.getArrayList();
   /*     if(ocrList==null)
        {
            L.d("ocrList==null");
        }
        L.d(ocrList.getArrayList().toString()+ "123");*/
        mRecyclerView = (RecyclerView) findViewById(R.id.nodecontext_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        NodecontextRecycleViewAdapter nodecontextRecycleViewAdapter= new NodecontextRecycleViewAdapter(this,arrayList);
        mRecyclerView.setAdapter(nodecontextRecycleViewAdapter);
        TextView textView = (TextView) findViewById(R.id.ocr_title_textview);
        textView.setText(ocrList.getList_name());
    }
}
