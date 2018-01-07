package com.xuexiang.xpage.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.List;

/**
 * 工具类
 * @author xuexiang
 * @date 2018/1/6 下午10:39
 */
public class Utils {
    /**
     * 获取resources对象
     *
     * @return
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 获取dimes值【px不会乘以denstiy.】
     *
     * @param resId
     * @return
     */
    public static int getDimensionPixelOffset(Context context, @DimenRes int resId) {
        return getResources(context).getDimensionPixelOffset(resId);
    }

    /**
     * 获取dimes值【getDimensionPixelSize则不管写的是dp还是sp还是px,都会乘以denstiy.】
     *
     * @param resId
     * @return
     */
    public static int getDimensionPixelSize(Context context, @DimenRes int resId) {
        return getResources(context).getDimensionPixelSize(resId);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 尺寸dip
     * @return 像素值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取资源图片【和主体有关】
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(Context context, @DrawableRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(resId);
        }
        return context.getResources().getDrawable(resId);
    }

    /**
     * 是否需要隐藏键盘
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationOnScreen(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            PageLog.d("left:" + left + ", top:" + top  + ", right:" + right + ", bottom:" + bottom);
            PageLog.d("event.getRawX():" + event.getRawX() + ", event.getX():" + event.getX() + ", event.getRawY():" + event.getRawY() + ", event.getY():" + event.getY());
            if (event.getRawX() > left && event.getRawX() < right && event.getRawY() > top && event.getRawY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏键盘
     * @param v
     */
    public static void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , InputMethodManager.HIDE_NOT_ALWAYS );
        }
    }

    public static <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }

    /**
     * 停止指定应用的所以运行的服务
     *
     * @param context
     */
    public static void stopAllRunningService(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> servicesList = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo si : servicesList) {
            if (context.getPackageName().equals(si.service.getPackageName())) {
                stopRunningService(context, si.service.getClassName());
            }
        }
    }

    /**
     * 停止运行服务
     *
     * @param context
     *            上下文
     * @param className
     *            类名
     * @return 是否执行成功
     */
    public static boolean stopRunningService(Context context, String className) {
        Intent intent_service = null;
        boolean ret = false;
        try {
            intent_service = new Intent(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (intent_service != null) {
            ret = context.stopService(intent_service);
        }
        return ret;
    }
}
