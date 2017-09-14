package com.competition.android.competition_five.Adapter.Base;

import android.content.Context;

import java.util.List;

/**
 * Created by lx on 2017/7/15.
 */

public abstract class SimpleAdapter <T> extends BaseAdapter<T,BaseViewHolder> {
    public SimpleAdapter(Context context, List<T> datas, int layoutResId) {
        super(context, datas, layoutResId);
    }
}
