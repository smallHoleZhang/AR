package com.competition.android.competition_five.tables;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

/**
 * Created by Vincent on 2017/9/14.
 */

public class BarrageTable {

    private static AVObject barrage;

    /**
     * 创建表
     */
    public static void  create_barrage(){

         barrage = new AVObject("BarrageTable");// 构建对象
    }

    private BarrageTable(){}

    public static AVObject getInstance(){

        return barrage;
    }

}
