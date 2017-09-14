package com.competition.android.competition_five.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.competition.android.competition_five.R;
import com.competition.android.competition_five.Uilt.OpenUile;
import com.competition.android.competition_five.activity.mine.FeedbackActivity;
import com.competition.android.competition_five.activity.mine.SettingActivity;
import com.competition.android.competition_five.activity.mine.UpdateUserInfoActivity;
import com.competition.android.competition_five.activity.mine.UserInfoActivity;

/**
 * Created by hasee on 2017/8/18.
 */

public class MineFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout user_info;

    private ImageView update_user_info;

    private RelativeLayout setting,feedback;

    private Intent mIntent;

    public static Fragment newInstance(){
        MineFragment mineFragment = new MineFragment();
        return mineFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_layout,container,false);
        OpenUile.setPrimaryDark(getActivity(),R.color.greyed);

        initWigdget(view);

        return view;
    }



    private void initWigdget(View view) {

        user_info = (RelativeLayout) view.findViewById(R.id.mine_info);

        update_user_info = (ImageView) view.findViewById(R.id.update_user_info);

        feedback = (RelativeLayout) view.findViewById(R.id.feedback);

        setting = (RelativeLayout) view.findViewById(R.id.setting);

        user_info.setOnClickListener(this);
        update_user_info.setOnClickListener(this);
        feedback.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.mine_info:
                mIntent = new Intent(getContext(), UserInfoActivity.class);

                startActivity(mIntent);

                break;
            case R.id.update_user_info:
                mIntent = new Intent(getContext(), UpdateUserInfoActivity.class);

                startActivity(mIntent);
                break;
            case R.id.feedback:
                mIntent = new Intent(getContext(), FeedbackActivity.class);

                startActivity(mIntent);
                break;
            case R.id.setting:
                mIntent = new Intent(getContext(), SettingActivity.class);

                startActivity(mIntent);
                break;

        }

    }
}