package com.xuexiang.xpage;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageActivity;
import com.xuexiang.xpage.core.CoreConfig;
import com.xuexiang.xpage.logger.PageLog;
import com.xuexiang.xpage.model.PageInfo;
import com.xuexiang.xpage.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面配置
 *
 * @author xuexiang
 * @since 2018/5/24 下午3:40
 */
public class PageConfig {
    private RefWatcher mRefWatcher;
    /**
     * 页面配置接口
     */
    private PageConfiguration mPageConfiguration;
    /**
     * 是否监测内存泄露
     */
    private boolean mIsEnableWatcher = true;

    private List<PageInfo> mPages = new ArrayList<>();

    /**
     * XPageFragment的容器Activity类名
     */
    private String mContainActivityClassName = XPageActivity.class.getCanonicalName();

    private static PageConfig gInstance;

    public static PageConfig getInstance() {
        if (gInstance == null) {
            synchronized (PageConfig.class) {
                if (gInstance == null) {
                    gInstance = new PageConfig();
                }
            }
        }
        return gInstance;
    }

    public PageConfig setPageConfiguration(PageConfiguration pageConfiguration) {
        mPageConfiguration = pageConfiguration;
        return this;
    }

    /**
     * 是否监控内存泄露
     *
     * @param enableWatcher
     * @return
     */
    public PageConfig enableWatcher(boolean enableWatcher) {
        mIsEnableWatcher = enableWatcher;
        return this;
    }

    public boolean isEnableWatcher() {
        return mIsEnableWatcher;
    }

    /**
     * 初始化页面配置
     *
     * @param application
     */
    public void init(Application application) {
        if (mIsEnableWatcher) {
            initCanary(application);
        }

        initPages(application);
    }

    /**
     * 设置调试模式
     *
     * @param tag
     * @return
     */
    public PageConfig debug(String tag) {
        PageLog.debug(tag);
        return this;
    }

    /**
     * 初始化页面信息
     *
     * @param context
     */
    private void initPages(Context context) {
        Utils.checkNotNull(mPageConfiguration, "mPageConfiguration == null");
        registerPageInfos(mPageConfiguration.registerPages(context));
        CoreConfig.init(context, getPages());
    }

    /**
     * 内存泄漏监听
     *
     * @param application
     */
    private void initCanary(Application application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            return;
        }
        mRefWatcher = LeakCanary.install(application);
    }

    public RefWatcher getRefWatcher() {
        return mRefWatcher;
    }


    /**
     * 设置XPageFragment的容器Activity类名
     *
     * @param containActivityClazz
     * @return
     */
    public PageConfig setContainActivityClazz(@NonNull Class<? extends XPageActivity> containActivityClazz) {
        mContainActivityClassName = containActivityClazz.getCanonicalName();
        return this;
    }

    public static String getContainActivityClassName() {
        return getInstance().mContainActivityClassName;
    }

    /**
     * 注册页面信息
     *
     * @param clazz
     * @return
     */
    public PageConfig registerPageInfo(Class<?> clazz) {
        mPages.add(getPageInfo(clazz));
        return this;
    }

    /**
     * 注册多个页面信息
     *
     * @param clazz
     * @return
     */
    public PageConfig registerPageInfos(Class... clazz) {
        for (int i = 0; i < clazz.length; i++) {
            registerPageInfo(clazz[i]);
        }
        return this;
    }

    /**
     * 注册多个页面信息
     *
     * @param pageInfos
     * @return
     */
    private PageConfig registerPageInfos(List<PageInfo> pageInfos) {
        if (pageInfos != null && pageInfos.size() > 0) {
            mPages.clear();
            mPages.addAll(pageInfos);
        }
        return this;
    }

    public List<PageInfo> getPages() {
        return mPages;
    }

    public static PageInfo getPageInfo(Class<?> clazz) {
        Page page = getPage(clazz);
        PageInfo pageInfo = new PageInfo(TextUtils.isEmpty(page.name()) ? clazz.getSimpleName() : page.name(), clazz);
        if (!TextUtils.isEmpty(page.params()[0])) {
            pageInfo.setParams(page.params());
        }
        pageInfo.setAnim(page.anim());
        return pageInfo;
    }

    /**
     * 获取页面信息
     *
     * @param clazz
     * @return
     */
    public static Page getPage(Class<?> clazz) {
        return Utils.checkNotNull(clazz.getAnnotation(Page.class), "Page == null，请检测页面是否漏加 @Page 进行修饰！");
    }
}
