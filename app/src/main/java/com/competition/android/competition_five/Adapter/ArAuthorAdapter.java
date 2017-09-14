package com.competition.android.competition_five.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.competition.android.competition_five.Adapter.Base.BaseViewHolder;
import com.competition.android.competition_five.Adapter.Base.SimpleAdapter;
import com.competition.android.competition_five.R;
import com.competition.android.competition_five.javaBean.UploadContent;

import java.util.List;
import java.util.Map;

/**
 * Created by Vincent on 2017/9/3.
 */

public class ArAuthorAdapter extends SimpleAdapter<UploadContent> {


    public ArAuthorAdapter(Context context, List<UploadContent> datas, int layoutResId) {
        super(context, datas, layoutResId);
    }

    @Override
    public void bindData(BaseViewHolder holder, UploadContent uploadContent) {

        TextView content = holder.getTextView(R.id.label);

        TextView author = holder.getTextView(R.id.author_name);
        TextView upload_time = holder.getTextView(R.id.upload_time);

        ImageView image = holder.getImageView(R.id.upload_image);

        content.setText(uploadContent.getContent());

        author.setText(uploadContent.getAuthor());

        upload_time.setText(uploadContent.getUpload_time());

        image.setImageBitmap(uploadContent.getBitmap());

    }
}
