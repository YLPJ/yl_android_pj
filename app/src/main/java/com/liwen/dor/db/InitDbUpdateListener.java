package com.liwen.dor.db;

import android.util.Log;


import com.liwen.dor.BuildConfig;
import com.liwen.dor.constant.SPConstant;
import com.liwen.dor.util.SpUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

/**
 * @auther by yushilei.
 * @time 2017/3/8-16:29
 * @desc
 */

public class InitDbUpdateListener implements DbManager.DbUpgradeListener {
    private static final String TAG = "InitDbUpdateListener";

    @Override
    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
        if (BuildConfig.DEBUG)
            Log.i(TAG, "InitDb :newVersion=" + newVersion + " ;oldVersion=" + oldVersion);
        if (newVersion > oldVersion) {
            //数据库有升级，从新设置初始化参数
            SpUtil.saveSP(SPConstant.SP_INIT, false);
            try {
                db.dropDb();
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }
}
