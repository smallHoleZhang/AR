package com.competition.android.competition_five.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.competition.android.competition_five.Entity.OcrList;
import com.competition.android.competition_five.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/8/20.
 */

public class NodecontextRecycleViewAdapter  extends RecyclerView.Adapter<NodecontextRecycleViewAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<String> mArrayList;
    private OcrRecycViewAdapter.OnItemClickListenr mOnItemClickListener;


    public NodecontextRecycleViewAdapter (Context context, ArrayList list)
    {
        this.mContext = context;
        this.mArrayList = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycview_ocrlist,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = mArrayList.get(position);
        holder.mocrlisttv.setText(text);
    }



    @Override
    public int getItemCount() {
        return mArrayList.size();
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
