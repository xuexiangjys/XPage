package com.xuexiang.xpage.base;

import com.xuexiang.xpage.PageConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的页面容器fragment，只需继承PageContainerListFragment， 重写getPagesClasses方法，把需要显示的页面的类传入即可。
 *
 * @author xuexiang
 * @since 2018/5/24 下午3:48
 */
public abstract class XPageContainerListFragment extends XPageSimpleListFragment {
    /**
     * 初始化页面容器内容
     *
     * @param lists
     * @return
     */
    @Override
    protected List<String> initSimpleData(List<String> lists) {
        return getSimplePageNames(getSimplePageClasses());
    }

    /**
     * 条目点击
     *
     * @param position
     */
    @Override
    protected void onItemClick(int position) {
        openPage(getSimpleDataItem(position));
    }


    @Override
    public Class[] getSimplePageClasses() {
        return getPagesClasses();
    }

    /**
     * 获取页面的类集合[使用@Page注解进行注册的页面]
     *
     * @return 页面的类集合
     */
    protected abstract Class[] getPagesClasses();

    /**
     * 获取页面名称集合
     *
     * @param classes 页面类集合
     * @return 页面名称集合
     */
    private List<String> getSimplePageNames(Class... classes) {
        List<String> simplePageList = new ArrayList<>();
        if (classes != null && classes.length > 0) {
            for (Class aClass : classes) {
                simplePageList.add(PageConfig.getPageInfo(aClass).getName());
            }
        }
        return simplePageList;
    }
}
