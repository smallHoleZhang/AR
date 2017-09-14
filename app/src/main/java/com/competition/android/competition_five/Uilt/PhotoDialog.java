package com.competition.android.competition_five.Uilt;

import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.competition.android.competition_five.R;


/**
 * Created by lx on 2017/7/18.
 */


/**
 * 显示选择拍照或者从相册中获取图片的Dialog
 */
public class PhotoDialog {

    private Context mContext;
    private LayoutInflater mInflater;
    private Dialog mDialog;
    protected SparseArray<View> views;
    private View mView;


    public PhotoDialog(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        views = new SparseArray<>();
    }

    public void show_dialog(){

        if (mDialog==null){
            mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
            mView = mInflater.inflate(R.layout.layout_upload_tupain_dialog, null);
            //将布局设置给Dialog
            mDialog.setContentView(mView);
            //获取当前Activity所在的窗体
            Window dialogWindow = mDialog.getWindow();
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity( Gravity.BOTTOM);
            //获得窗体的属性
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//       将属性设置给窗体
            dialogWindow.setAttributes(lp);
            mDialog.show();//显示对话框
        }
    }

    public TextView getTextView(int id){
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
