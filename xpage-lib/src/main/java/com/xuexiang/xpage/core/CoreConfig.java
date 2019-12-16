package com.xuexiang.xpage.core;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.xuexiang.xpage.model.PageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局配置类
 *
 * @author xuexiang
 * @since 2018/5/24 下午3:47
 */
public class CoreConfig {
    /**
     * Atlas支持 start
     */
    private static boolean isOpenAtlas = false;
    private static ClassLoader mBundleClassLoader = null;

    public static boolean isOpenAtlas() {
        return isOpenAtlas;
    }

    public static void setIsOpenAtlas(boolean isOpenAtlasFlag) {
        isOpenAtlas = isOpenAtlasFlag;
    }

    public static ClassLoader getBundleClassLoader() {
        return mBundleClassLoader;
    }

    public static void setBundleClassLoader(ClassLoader classLoader) {
        mBundleClassLoader = classLoader;
    }

    private static Context sContext;

    /**
     * 默认初始化，配置文件在assets/corepage.json
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        sContext = context.getApplicationContext();
        CorePageManager.getInstance().init(sContext);
    }

    /**
     * 自定义初始化，配置文件信息由外部传入。
     *
     * @param context  上下文
     * @param pageJson 配置的json
     */
    public static void init(Context context, String pageJson) {
        sContext = context.getApplicationContext();
        CorePageManager.getInstance().init(sContext, pageJson);
    }

    /**
     * 自定义初始化，配置文件信息由外部传入。
     *
     * @param context      上下文
     * @param pageInfoList 配置的页面信息
     */
    public static void init(Context context, List<PageInfo> pageInfoList) {
        CoreConfig.init(context, JSON.toJSONString(pageInfoList));
    }

    /**
     * 自定义初始化，配置文件信息由外部传入。
     *
     * @param context  上下文
     * @param pageInfo 配置的页面信息
     */
    public static void init(Context context, PageInfo pageInfo) {
        List<PageInfo> list = new ArrayList<PageInfo>();
        list.add(pageInfo);
        CoreConfig.init(context, list);
    }

    public static void readConfig(String pageJson) {
        CorePageManager.getInstance().readConfig(pageJson);
    }


    public static Context getContext() {
        testInitialize();
        return sContext;
    }

    private static void testInitialize() {
        if (sContext == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用 PageConfig.init() 初始化！");
        }
    }
}
