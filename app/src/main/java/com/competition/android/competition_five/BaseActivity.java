package com.competition.android.competition_five;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * 项目名： begin
 * 包名：   com.bignerdranch.android.begin
 * 文件名： BaseActivity
 * 创建者： Zhang
 * 描述：   Acyivity基类
 */

/**
 * 1.to
 */
public class BaseActivity extends AppCompatActivity {

/*    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }*/
    //菜单栏操作

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
