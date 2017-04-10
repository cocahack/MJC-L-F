package com.programmer.awesome.mjclnf.customObject;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by USER on 2016-11-14.
 */

public class SystemInfo {
    private static Activity mActivity;
    private static DisplayMetrics metrics = new DisplayMetrics();
    public SystemInfo(Activity activity)
    {
        mActivity = activity;
    }
    public static DisplayMetrics getDisplayMetrics()
    {
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }
}
