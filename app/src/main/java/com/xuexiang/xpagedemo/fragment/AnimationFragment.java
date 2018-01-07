package com.xuexiang.xpagedemo.fragment;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.ListSimpleFragment;
import com.xuexiang.xpage.core.CoreAnim;

import java.util.List;

/**
 * @author xuexiang
 * @date 2018/1/7 下午11:22
 */
@Page(name = "切换动画")
public class AnimationFragment extends ListSimpleFragment {

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
        return lists;
    }

    /**
     * 条目点击
     *
     * @param position
     */
    @Override
    protected void onItemClick(int position) {
        switch(position) {
            case 0:
                openPage(TestFragment.PAGE_NAME, null, CoreAnim.none);
                break;
            case 1:
                openPage(TestFragment.PAGE_NAME, null, CoreAnim.present);
                break;
            case 2:
                openPage(TestFragment.PAGE_NAME, null, CoreAnim.slide);
                break;
            case 3:
                openPage(TestFragment.PAGE_NAME, null, CoreAnim.fade);
                break;
            case 4:
                openPage(TestFragment.PAGE_NAME, null, CoreAnim.zoom);
                break;
            default:
                break;
        }
    }
}
