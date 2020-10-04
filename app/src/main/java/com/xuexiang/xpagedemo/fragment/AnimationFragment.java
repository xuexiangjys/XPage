package com.xuexiang.xpagedemo.fragment;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageSimpleListFragment;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpagedemo.R;

import java.util.List;

/**
 * @author xuexiang
 * @date 2018/1/7 下午11:22
 */
@Page(name = "切换动画")
public class AnimationFragment extends XPageSimpleListFragment {

    /**
     * 初始化例子
     *
     * @param lists
     * @return
     */
    @Override
    protected List<String> initSimpleData(List<String> lists) {
        lists.add("没有动画");
        lists.add("由下到上动画");
        lists.add("从左到右动画");
        lists.add("渐变");
        lists.add("放大");
        lists.add("自定义动画");
        return lists;
    }

    /**
     * 条目点击
     *
     * @param position
     */
    @Override
    protected void onItemClick(int position) {
        switch (position) {
            case 0:
                //没有动画
                openPage(TestFragment.PAGE_NAME, null, CoreAnim.none);
                break;
            case 1:
                //由下到上动画
                openPage(TestFragment.PAGE_NAME, null, CoreAnim.present);
                break;
            case 2:
                //从左到右动画
                openPage(TestFragment.PAGE_NAME, null, CoreAnim.slide);
                break;
            case 3:
                //渐变
                openPage(TestFragment.PAGE_NAME, null, CoreAnim.fade);
                break;
            case 4:
                //放大
                openPage(TestFragment.PAGE_NAME, null, CoreAnim.zoom);
                break;
            case 5:
                //自定义动画
                openPage(TestFragment.PAGE_NAME, null, new int[]{
                        // OpenEnterAnimation, 页面打开进场动画
                        R.anim.custom_open_enter,
                        // OpenExitAnimation, 页面打开退场动画
                        R.anim.custom_open_exit,

                        // CloseEnterAnimation, 页面关闭进场动画
                        R.anim.custom_close_enter,
                        // CloseExitAnimation, 页面关闭退场动画
                        R.anim.custom_close_exit
                });
                break;
            default:
                break;
        }
    }
}
