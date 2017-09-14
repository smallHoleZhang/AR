package com.competition.android.competition_five.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.competition.android.competition_five.Entity.OcrList;
import com.competition.android.competition_five.R;
import com.competition.android.competition_five.Uilt.L;

import java.util.List;

/**
 * Created by hasee on 2017/8/19.
 */

public class OcrRecycViewAdapter  extends RecyclerView.Adapter<OcrRecycViewAdapter.ViewHolder>  {
    private Context mContext;
    private List<OcrList> mList;
    private OnItemClickListenr mOnItemClickListener;

    public  OcrRecycViewAdapter(Context context, List<OcrList> list)
    {
        this.mContext = context;
        this.mList = list;
    }
    public  interface OnItemClickListenr{

        void onItemClick(View view, int position,OcrList ocrList);
    }
    public void setOnItemClickLitsener(OnItemClickListenr mOnItemClickLitsener)
    {
        mOnItemClickListener= mOnItemClickLitsener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycview_ocrlist,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String text = mList.get(position).getList_name();
        holder.mocrlisttv.setText(text);
        holder.mOcrList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.itemView,position,mList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static  class  ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        CardView mOcrList;
        TextView mocrlisttv;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mOcrList = (CardView) itemView.findViewById(R.id.ocr_list_cardview);
            mocrlisttv = (TextView) itemView.findViewById(R.id.ocr_list_textview);

        }
    }
}
