package com.xuexiang.xpage.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleableRes;
import androidx.appcompat.content.res.AppCompatResources;

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
     * 获取dimes值，返回的是【去余取整】的值
     *
     * @param resId
     * @return
     */
    public static int getDimensionPixelOffset(Context context, @DimenRes int resId) {
        return getResources(context).getDimensionPixelOffset(resId);
    }

    /**
     * 获取dimes值，返回的是【4舍5入】的值
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
        page.setContainActivityClassName(pageOption.getContainActivityClassName());
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
            // 如果点击事件落到了另一个　EditText 上, 则不隐藏键盘
            if (childInterceptEvent((ViewGroup) v.getRootView(), (int) event.getX(), (int) event.getY())) {
                return false;
            }

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

    // 递归遍历当前 View 树
    private static boolean childInterceptEvent(ViewGroup parentView, int touchX, int touchY) {
        boolean isConsume = false;
        for (int i = parentView.getChildCount() - 1; i >= 0; i--) {
            View childView = parentView.getChildAt(i);
            if (!childView.isShown()) {
                continue;
            }
            // 判断 view 是否在触摸区域内
            boolean isTouchView = isTouchView(touchX, touchY, childView);
            if (!isTouchView) {
                continue;
            }
            // 判断点击的是否是 EditText
            if (childView instanceof EditText) {
                isConsume = true;
            }
            // 递归遍历当前 View 树
            else if (childView instanceof ViewGroup) {
                ViewGroup itemView = (ViewGroup) childView;
                isConsume = childInterceptEvent(itemView, touchX, touchY);
                if (isConsume) {
                    break;
                }
            }
        }
        return isConsume;
    }

    // 判断view是否在触摸区域内
    private static boolean isTouchView(int touchX, int touchY, View view) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        return rect.contains(touchX, touchY);
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

    /**
     * 获取Drawable属性（兼容VectorDrawable）
     * @param context
     * @param typedArray
     * @param index
     * @return
     */
    public static Drawable getDrawableAttrRes(Context context, TypedArray typedArray, @StyleableRes int index) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return typedArray.getDrawable(index);
        } else {
            int resourceId = typedArray.getResourceId(index, -1);
            if (resourceId != -1) {
                return AppCompatResources.getDrawable(context, resourceId);
            }
        }
        return null;
    }


    public static Drawable resolveDrawable(Context context, @AttrRes int attr) {
        return resolveDrawable(context, attr, null);
    }

    public static Drawable resolveDrawable(
            Context context,
            @AttrRes int attr,
            @SuppressWarnings("SameParameterValue") Drawable fallback) {
        TypedArray array = context.getTheme().obtainStyledAttributes(new int[]{attr});
        try {
            Drawable drawable = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = array.getDrawable(0);
            } else {
                int id = array.getResourceId(0, -1);
                if (id != -1) {
                    drawable = AppCompatResources.getDrawable(context, id);
                }
            }
            if (drawable == null && fallback != null) {
                drawable = fallback;
            }
            return drawable;
        } finally {
            array.recycle();
        }
    }

    /**
     * 获取svg资源图片
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getVectorDrawable(Context context, @DrawableRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(resId);
        }
        return AppCompatResources.getDrawable(context, resId);
    }



    public static boolean resolveBoolean(Context context, @AttrRes int attr, boolean fallback) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        try {
            return a.getBoolean(0, fallback);
        } finally {
            a.recycle();
        }
    }

    public static boolean resolveBoolean(Context context, @AttrRes int attr) {
        return resolveBoolean(context, attr, false);
    }

}
