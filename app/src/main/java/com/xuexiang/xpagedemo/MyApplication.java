package com.xuexiang.xpagedemo;

import android.app.Application;

import com.xuexiang.xpage.PageConfig;
import com.xuexiang.xpage.base.XPageActivity;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xutil.XUtil;

/**
 * @author xuexiang
 * @date 2018/1/7 下午6:40
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        XUtil.init(this);
        XUtil.debug(isDebug());

        PageConfig.getInstance()
//                //页面注册,默认不设置的话使用的就是自动注册
//                .setPageConfiguration(new AutoPageConfiguration())
                //开启调试
                .debug(isDebug())
                //设置默认的容器Activity
                .setContainActivityClazz(XPageActivity.class)
                //初始化页面配置
                .init(this);

        initRouter();
    }

    private void initRouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (isDebug()) {
            XRouter.openLog();     // 打印日志
            XRouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        XRouter.init(this);
    }


    /**
     * @return 当前app是否是调试开发模式
     */
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
