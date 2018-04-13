package com.xuexiang.xpage.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * 管理toast的类，整个app用该类来显示toast
 */
public class ToastUtil {

    private static ToastUtil sInstance = null;

    private Context mContext;

    /**
     * 主线程Handler
     */
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());

    private Toast mToast = null;


    private ToastUtil(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 是否是主线程
     *
     * @return 是否是主线程
     */
    private boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public static ToastUtil getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ToastUtil.class) {
                if (sInstance == null) {
                    sInstance = new ToastUtil(context);
                }
            }
        }
        return sInstance;
    }

    public void showToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public void showToast(int resId) {
        showToast(mContext.getResources().getString(resId), Toast.LENGTH_SHORT);
    }


    /**
     * 提示信息
     *
     * @param msg
     * @param duration
     */
    public void showToast(final String msg, final int duration) {
        if (isMainThread()) {
            toast(msg, duration);
        } else {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    toast(msg, duration);
                }
            });
        }
    }

    /**
     * 显示消息
     * @param text     提示信息
     * @param duration 提示长度
     */
    private void toast(String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

}
