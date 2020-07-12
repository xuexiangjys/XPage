package com.xuexiang.xpage.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.xuexiang.xpage.R;

/**
 * 导航栏工具
 *
 * @author xuexiang
 * @since 2018/5/24 下午3:41
 */
public final class TitleUtils {

    private TitleUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    //=================================静态添加=======================================//

    /**
     * 利用TitleBar初始化ActionBar
     */
    public static TitleBar initTitleBar(final Activity activity, int titleBarResId, String title) {
        TitleBar titleBar = (TitleBar) activity.findViewById(titleBarResId);
        initTitleBarStyle(titleBar, title, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        return titleBar;
    }

    /**
     * 利用TitleBar初始化ActionBar
     */
    public static TitleBar initTitleBar(ViewGroup viewGroup, int titleBarResId, String title, View.OnClickListener listener) {
        TitleBar titleBar = (TitleBar) viewGroup.findViewById(titleBarResId);
        initTitleBarStyle(titleBar, title, listener);
        return titleBar;
    }
    //=================================动态添加=======================================//

    /**
     * 动态添加TitleBar
     *
     * @param activity
     * @param title
     */
    public static TitleBar addTitleBarDynamic(final Activity activity, String title) {
        TitleBar titleBar = initTitleBarDynamic(activity, title, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        getRootView(activity).addView(titleBar, 0);
        return titleBar;
    }

    /**
     * 动态添加TitleBar
     *
     * @param viewGroup
     * @param title
     * @param listener
     */
    public static TitleBar addTitleBarDynamic(ViewGroup viewGroup, String title, View.OnClickListener listener) {
        TitleBar titleBar = initTitleBarDynamic(viewGroup.getContext(), title, listener);
        viewGroup.addView(titleBar, 0);
        return titleBar;
    }

    /**
     * 动态生成TitleBar
     *
     * @param fragment
     * @param title
     * @return
     */
    public static TitleBar initTitleBarDynamic(final Fragment fragment, String title) {
        TitleBar titleBar = initTitleBarDynamic(fragment.getContext(), title, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.getActivity().finish();
            }
        });
        return titleBar;
    }

    /**
     * 动态添加TitleBar
     *
     * @param fragment
     * @param viewGroup
     * @param title
     */
    public static TitleBar addTitleBarDynamic(final Fragment fragment, ViewGroup viewGroup, String title) {
        TitleBar titleBar = initTitleBarDynamic(fragment, title);
        viewGroup.addView(titleBar, 0);
        return titleBar;
    }

    /**
     * 动态生成TitleBar
     *
     * @param context
     * @param title    标题
     * @param listener 左侧监听
     * @return
     */
    public static TitleBar initTitleBarDynamic(Context context, String title, View.OnClickListener listener) {
        TitleBar titleBar = new TitleBar(context);
        RelativeLayout.LayoutParams titleBarParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleBarParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        titleBar.setLayoutParams(titleBarParams);

        initTitleBarStyle(titleBar, title, listener);

        return titleBar;
    }

    /**
     * 获取setContentView的父布局
     *
     * @param activity
     * @return
     */
    public static ViewGroup getRootView(Activity activity) {
        return (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
    }

    /**
     * 初始化title的样式
     *
     * @param titleBar
     * @param title
     * @param listener
     * @return
     */
    public static TitleBar initTitleBarStyle(TitleBar titleBar, String title, View.OnClickListener listener) {
        titleBar.setLeftImageDrawable(Utils.resolveDrawable(titleBar.getContext(), R.attr.xpage_actionbar_navigation_back, Utils.getVectorDrawable(titleBar.getContext(), R.drawable.xpage_ic_navigation_back_white)))
                .setLeftClickListener(listener)
                .setTitle(title);
        return titleBar;
    }
}
