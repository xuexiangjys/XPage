package com.xuexiang.xpagedemo;

import android.content.Context;

import com.xuexiang.xpage.AppPageConfig;
import com.xuexiang.xpage.PageConfig;
import com.xuexiang.xpage.PageConfiguration;
import com.xuexiang.xpage.base.ListSimpleFragment;
import com.xuexiang.xpage.base.app.BaseApplication;
import com.xuexiang.xpage.model.PageInfo;
import com.xuexiang.xpage.utils.ToastUtil;
import com.xuexiang.xpagedemo.fragment.DateReceiveFragment;
import com.xuexiang.xpagedemo.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author xuexiang
 * @date 2018/1/7 下午6:40
 */
public class MyApplication extends BaseApplication {

    /**
     * 初始化程序
     */
    @Override
    protected void init() {
        PageConfig.getInstance().setPageConfiguration(new PageConfiguration() {
            @Override
            public List<PageInfo> registerPages(Context context) {
//                List<PageInfo> pageInfos = new ArrayList<>();
//                addPageInfoAndSubPages(pageInfos, MainFragment.class);
//                pageInfos.add(PageConfig.getPageInfo(DateReceiveFragment.class));
                return AppPageConfig.getInstance().getPages();
            }
        }).debug("PageLog").init(this);

    }


    /**
     * 增加组件信息和子演示页信息
     * @param clazz 【继承了ListSimpleFragment的类】
     */
    private void addPageInfoAndSubPages(List<PageInfo> pageInfos, Class<? extends ListSimpleFragment> clazz) {
        pageInfos.add(PageConfig.getPageInfo(clazz));
        try {
            registerPageInfos(pageInfos, clazz.newInstance().getSimpleDataClazzes());
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
        for (int i = 0; i < clazz.length; i ++) {
            pageInfos.add(PageConfig.getPageInfo(clazz[i]));
        }
    }

    /**
     * 双击退出函数
     */
    private static boolean gIsExit = false;

    public static void exitBy2Click(Context context) {
        if (!gIsExit) {
            gIsExit = true; // 准备退出
            ToastUtil.getInstance(context).showToast("再按一次退出程序");
            Timer tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    gIsExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            MyApplication.exitApp(context);
        }
    }

}
