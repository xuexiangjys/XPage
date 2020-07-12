package com.xuexiang.xpage.core;

import androidx.fragment.app.Fragment;

import com.xuexiang.xpage.base.XPageFragment;

import java.util.List;

/**
 * 页面跳转接口，用于控制页面跳转或启动新的activity
 *
 * @author xuexiang
 * @since 2018/5/24 下午3:47
 */
public interface CoreSwitcher {
    /**
     * 返回到某一个页面（只有一个fragment时会关闭Activityt）
     */
    void popPage();

    /**
     * fragmentTag 是否在当前顶上activity上的最顶上的fragment
     *
     * @param fragmentTag fragment的tag
     * @return 是否位于栈顶
     */
    boolean isFragmentTop(String fragmentTag);


    /**
     * 是否查找到某个page
     *
     * @param pageName 页面名
     * @return 是否找到
     */
    boolean findPage(final String pageName);

    /**
     * 跳转到某一个页面。
     *
     * @param bean CoreSwitchBean对象
     * @return 打开的页面Fragment对象
     */
    Fragment gotoPage(CoreSwitchBean bean);

    /**
     * 打开一个新的页面
     *
     * @param bean  CoreSwitchBean对象
     * @return 打开的页面Fragment对象
     */
    Fragment openPage(CoreSwitchBean bean);

    /**
     * 切换页面（不重新加载一个新的fragment）
     *
     * @param bean  CoreSwitchBean对象
     * @return 打开的页面Fragment对象
     */
    Fragment changePage(CoreSwitchBean bean);

    /**
     * 移除当前Activity不需要的fragment
     *
     * @param fragmentLists 无用fragment列表
     */
    void removeUnlessFragment(List<String> fragmentLists);

    /**
     * 页面跳转，支持跨Activity进行传递数据
     *
     * @param page CoreSwitchBean对象
     * @param fragment BaseFragment对象
     * @return 打开的页面Fragment对象
     */
    Fragment openPageForResult(final CoreSwitchBean page, final XPageFragment fragment);

}
