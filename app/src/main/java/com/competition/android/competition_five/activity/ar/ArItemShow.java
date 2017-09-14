package com.competition.android.competition_five.activity.ar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.competition.android.competition_five.Adapter.Base.BaseAdapter;
import com.competition.android.competition_five.R;

/**
 * Created by Vincent on 2017/9/8.
 */

public class ArItemShow extends BaseActivity implements View.OnClickListener{

    private ImageButton mBack;

    private TextView mTitle;

    @Override
    protected Fragment createFragment() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ar_item_show);


        String title = getIntent().getStringExtra("title");


        mBack = (ImageButton) this.findViewById(R.id.back_btn);

        mTitle = (TextView) this.findViewById(R.id.back_title_text);

        mTitle.setText(title);

        mBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
        }
    }
}
