package com.xuexiang.xpage.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.xuexiang.xpage.core.CoreSwitchBean;
import com.xuexiang.xpage.core.PageOption;

/**
 * 工具类
 *
 * @author xuexiang
 * @since 2018/5/24 下午3:50
 */
public final class Utils {

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取resources对象
     *
     * @return
     */
    private static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 获取dimes值【px不会乘以Denstiy.】
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

    public static int resolveDimension(Context context, @AttrRes int attr, int fallback) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[] {attr});
        try {
            return a.getDimensionPixelSize(0, fallback);
        } finally {
            a.recycle();
        }
    }

    @ColorInt
    public static int resolveColor(Context context, @AttrRes int attr, int fallback) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[] {attr});
        try {
            return a.getColor(0, fallback);
        } finally {
            a.recycle();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 尺寸dip
     * @return 像素值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     *
     * @param pxValue 尺寸像素
     * @return SP值
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
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

    public static CoreSwitchBean toSwitch(@NonNull PageOption pageOption) {
        CoreSwitchBean page = new CoreSwitchBean(pageOption.getPageName(), pageOption.getBundle(), pageOption.getAnim(), pageOption.isAddToBackStack(), pageOption.isNewActivity());
        page.setRequestCode(pageOption.getRequestCode());
        return page;
    }

    /**
     * 是否需要隐藏键盘
     *
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationOnScreen(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getRawX() > left) || !(event.getRawX() < right) || !(event.getRawY() > top) || !(event.getRawY() < bottom);
        }
        return false;
    }

    /**
     * 动态隐藏软键盘
     *
     * @param view 视图
     */
    public static void hideSoftInput(final View view) {
        if (view == null) {
            return;
        }
        InputMethodManager imm =
                (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }

}
