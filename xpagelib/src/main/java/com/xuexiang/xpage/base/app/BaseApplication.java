package com.xuexiang.xpage.base.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.xuexiang.xpage.utils.Utils;

/**
 * @author xuexiang
 * @date 2018/1/7 下午9:26
 */
public abstract class BaseApplication extends Application {

    // 全局对象
    private static ActivityLifecycleHelper mActivityLifecycleHelper;
    private static Context mContext;
    private static Handler mAppHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppHandler = new Handler();
        mContext = this;
        mActivityLifecycleHelper = new ActivityLifecycleHelper();
        registerActivityLifecycleCallbacks(mActivityLifecycleHelper);
        init();
    }

    /**
     * 初始化程序
     */
    protected abstract void init();

    public static Handler getAppHandler() {
        return mAppHandler;
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 退出程序
     */
    public static void exitApp(Context context) {
        Utils.stopAllRunningService(context);
        if (mActivityLifecycleHelper != null) {
            mActivityLifecycleHelper.finishAllActivity();
        }
        System.exit(0);
    }

}
