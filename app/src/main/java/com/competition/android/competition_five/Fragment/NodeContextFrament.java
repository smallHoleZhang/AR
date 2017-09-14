package com.competition.android.competition_five.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.competition.android.competition_five.R;

/**
 * Created by hasee on 2017/8/20.
 */

public class NodeContextFrament extends Fragment {

    public static Fragment newInstance(){
        NodeContextFrament nodeContextFrament = new NodeContextFrament();
        return nodeContextFrament;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_nodecontext_layout,container,false);
        return view;
    }
}
