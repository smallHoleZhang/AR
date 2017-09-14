package com.competition.android.competition_five.Adapter.Base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lx on 2017/7/15.
 */

public abstract class BaseAdapter<T,H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected int mLayoutResId;
    protected OnItemClickListener mListener;

    public BaseAdapter(Context context,List<T> datas,int layoutResId){
        mDatas = datas;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mLayoutResId = layoutResId;
    }

    public interface OnItemClickListener{
        void OnClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(mLayoutResId,parent,false);

        return new BaseViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T t = getItem(position);

        bindData(holder,t);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public T getItem(int position){
        return mDatas.get(position);
    }

    public abstract void bindData(BaseViewHolder holder,T t);

}
