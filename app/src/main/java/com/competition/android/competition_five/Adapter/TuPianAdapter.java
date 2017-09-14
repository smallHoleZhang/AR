package com.competition.android.competition_five.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;


import com.competition.android.competition_five.Adapter.Base.BaseViewHolder;
import com.competition.android.competition_five.Adapter.Base.SimpleAdapter;
import com.competition.android.competition_five.R;
import com.competition.android.competition_five.javaBean.TuPian;

import java.util.List;

/**
 * Created by lx on 2017/7/17.
 */

public class TuPianAdapter extends SimpleAdapter<TuPian> {

    private Context mContext;
    private BaseViewHolder mHolder;
    private View mItemView;

    public TuPianAdapter(Context context, List<TuPian> datas, int layoutResId) {
        super(context, datas, layoutResId);
        this.mContext = context;
    }

    @Override
    public void bindData(BaseViewHolder holder, TuPian tuPian) {

        mHolder = holder;

        ImageView image = holder.getImageView(R.id.upload_tupian_imageView);
        Bitmap bitmap = null;
        if (tuPian.getImagePath() == null) {
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.addphoto);
        } else {
            bitmap = createImageThumbnail(tuPian.getImagePath());
        }
        image.setImageBitmap(bitmap);

        CheckBox checkBox = holder.getCheckBox(R.id.upload_tupian_checkBox);

        checkBox.setVisibility(tuPian.getCheckBoxVisible()==1?View.VISIBLE:View.GONE);

    }


    public static Bitmap createImageThumbnail(String filePath) {
        Bitmap bitmap = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);
        opts.inSampleSize = computeSampleSize(opts, -1, 128 * 128);
        opts.inJustDecodeBounds = false;
        try {
            bitmap = BitmapFactory.decodeFile(filePath, opts);
        } catch (Exception e) {

        }
        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

}
