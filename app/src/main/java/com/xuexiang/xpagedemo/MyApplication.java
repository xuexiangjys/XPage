package com.xuexiang.xpagedemo;

import android.app.Application;
import android.content.Context;

import com.xuexiang.xpage.AppPageConfig;
import com.xuexiang.xpage.PageConfig;
import com.xuexiang.xpage.PageConfiguration;
import com.xuexiang.xpage.base.SimpleListFragment;
import com.xuexiang.xpage.model.PageInfo;
import com.xuexiang.xutil.XUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author xuexiang
 * @date 2018/1/7 下午6:40
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        XUtil.init(this);
        XUtil.debug(true);

        PageConfig.getInstance().setPageConfiguration(new PageConfiguration() {
            @Override
            public List<PageInfo> registerPages(Context context) {
//                List<PageInfo> pageInfos = new ArrayList<>();
//                addPageInfoAndSubPages(pageInfos, MainFragment.class);
//                pageInfos.add(PageConfig.getPageInfo(DateReceiveFragment.class));
                return AppPageConfig.getInstance().getPages();
            }
        }).debug("PageLog").enableWatcher(false).init(this);
    }

    /**
     * 增加组件信息和子演示页信息
     * @param clazz 【继承了ListSimpleFragment的类】
     */
    private void addPageInfoAndSubPages(List<PageInfo> pageInfos, Class<? extends SimpleListFragment> clazz) {
        pageInfos.add(PageConfig.getPageInfo(clazz));
        try {
            registerPageInfos(pageInfos, clazz.newInstance().getSimplePageClasses());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册多个页面信息
     * @param clazz
     * @return
     */
    private void registerPageInfos(List<PageInfo> pageInfos, Class... clazz) {
        for (Class aClazz : clazz) {
            pageInfos.add(PageConfig.getPageInfo(aClazz));
        }
    }

}
