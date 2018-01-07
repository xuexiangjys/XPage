package com.xuexiang.xpage.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 管理toast的类，整个app用该类来显示toast
 */
public class ToastUtil {
    private static ToastUtil mInstance = null;

    private Toast mToast = null;
    private Context mContext;

    private ToastUtil(Context context) {
        mContext = context;
    }

    public synchronized static ToastUtil getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new ToastUtil(ctx.getApplicationContext());
        }
        return mInstance;
    }

    /**
     *
     * @param text 提示信息
     * @param duration 提示长度
     */
    public void showToast(String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    public void showToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public void showToast(int resId) {
        showToast(mContext.getResources().getString(resId), Toast.LENGTH_SHORT);
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

}
