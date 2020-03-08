package com.xuexiang.xpagedemo;

import android.app.Application;
import android.content.Context;

import com.xuexiang.xpage.AppPageConfig;
import com.xuexiang.xpage.PageConfig;
import com.xuexiang.xpage.PageConfiguration;
import com.xuexiang.xpage.base.XPageActivity;
import com.xuexiang.xpage.base.XPageSimpleListFragment;
import com.xuexiang.xpage.model.PageInfo;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xutil.XUtil;

import java.util.List;

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
                .setPageConfiguration(new PageConfiguration() { //页面注册
                    @Override
                    public List<PageInfo> registerPages(Context context) {
                        //自动注册页面,是编译时自动生成的，build一下就出来了
                        return AppPageConfig.getInstance().getPages();
                    }
                })
                .debug("PageLog")       //开启调试
                .enableWatcher(isDebug())   //设置是否开启内存泄露监测
                .setContainActivityClazz(XPageActivity.class) //设置默认的容器Activity
                .init(this);   //初始化页面配置

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
