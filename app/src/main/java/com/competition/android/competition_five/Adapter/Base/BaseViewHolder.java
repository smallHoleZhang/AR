package com.competition.android.competition_five.Adapter.Base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lx on 2017/7/15.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    protected SparseArray<View> views;

    protected View mItemView;
    protected BaseAdapter.OnItemClickListener mListener;

    private int mPosition;


    public BaseViewHolder(View itemView, BaseAdapter.OnItemClickListener listener) {
        super(itemView);

        views = new SparseArray<>();
        this.mItemView = itemView;
        this.mListener = listener;
        mItemView.setOnClickListener(this);
    }

    /**
     * 获取View
     * @param id
     * @return
     */
    public View getView(int id){
        return findView(id);
    }

    /**
     * 获取TextView
     * @param id
     * @return
     */
    public TextView getTextView(int id){
        return findView(id);
    }

    /**
     * 获取ImageView
     * @param id
     * @return
     */
    public CheckBox getCheckBox(int id){
        return findView(id);
    }

    /**
     * 获取ImageView
     * @param id
     * @return
     */
    public ImageView getImageView(int id){
        return findView(id);
    }

    /**
     * 获取Button
     * @param id
     * @return
     */
    public Button getButton(int id){
        return findView(id);
    }

    public <T extends View> T findView(int id){
        View view = views.get(id);
        if (view==null){
            view = mItemView.findViewById(id);
            views.put(id,view);
        }
        return (T) view;
    }

    @Override
    public void onClick(View v) {
        if (mListener!=null) {

            setPosition(getLayoutPosition());
            mListener.OnClick(v,getLayoutPosition());
        }
    }


    public int getMPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }
}
