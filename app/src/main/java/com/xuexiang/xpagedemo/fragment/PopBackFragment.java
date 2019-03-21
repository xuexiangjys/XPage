package com.xuexiang.xpagedemo.fragment;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageSimpleListFragment;

import java.util.List;

/**
 * @author XUE
 * @since 2019/3/21 10:42
 */
@Page(name = "测试PopBack")
public class PopBackFragment extends XPageSimpleListFragment {
    /**
     * 初始化例子
     *
     * @param lists
     * @return
     */
    @Override
    protected List<String> initSimpleData(List<String> lists) {
        lists.add("AddToBackStack = true");
        lists.add("AddToBackStack = false");
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
                openPage(PopBackFragment.class, true);
                break;
            case 1:
                openPage(PopBackFragment.class, false);
                break;
            default:
                break;
        }
    }
}
