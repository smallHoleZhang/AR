package com.competition.android.competition_five.Uilt;

import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.competition.android.competition_five.R;

/**
 * Created by Vincent on 2017/9/3.
 */

public class UploadDialog {

    private Context mContext;
    private LayoutInflater mInflater;
    private Dialog mDialog;
    protected SparseArray<View> views;
    private View mView;


    public UploadDialog(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        views = new SparseArray<>();
    }

    public void show_dialog(){

        if (mDialog==null){
            mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
            mView = mInflater.inflate(R.layout.layout_upload_dialog, null);
            //将布局设置给Dialog
            mDialog.setContentView(mView);
            //获取当前Activity所在的窗体
            Window dialogWindow = mDialog.getWindow();
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity( Gravity.TOP);
            //获得窗体的属性
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            //将属性设置给窗体
            dialogWindow.setAttributes(lp);
            mDialog.show();//显示对话框
        }
    }

    public TextView getTextView(int id){
        return findView(id);
    }

    public Button getButton(int id){
        return findView(id);
    }

    public RelativeLayout getRelativeLayout(int id){
        return findView(id);
    }

    public <T extends View> T findView(int id){
        View view = views.get(id);
        if (view==null){
            view = mView.findViewById(id);
            views.put(id,view);
        }
        return (T) view;
    }


    /**
     * 将Dialog关闭
     */
    public void cancel_dialog(){
        mDialog.cancel();
    }

}
