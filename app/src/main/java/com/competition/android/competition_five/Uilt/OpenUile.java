package com.competition.android.competition_five.Uilt;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import com.competition.android.competition_five.R;

/**
 * Created by hasee on 2017/8/19.
 */

public class OpenUile {
    public static void setPrimaryDark(Activity activity,int color)
    {
        Window window = activity.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(activity.getResources().getColor(color));
    }

}
