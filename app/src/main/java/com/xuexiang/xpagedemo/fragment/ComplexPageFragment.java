package com.xuexiang.xpagedemo.fragment;

import android.content.Intent;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageSimpleListFragment;
import com.xuexiang.xpagedemo.ComplexActivity;

import java.util.List;

/**
 * @author xuexiang
 * @since 2018/5/30 下午6:11
 */
@Page(name = "复杂使用")
public class ComplexPageFragment extends XPageSimpleListFragment  {
    /**
     * 初始化例子
     *
     * @param lists
     * @return
     */
    @Override
    protected List<String> initSimpleData(List<String> lists) {
        lists.add("复杂页面使用（非ViewPager）");
        lists.add("Tab主页使用（ViewPager）");
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
                startActivity(new Intent(getContext(), ComplexActivity.class));
                break;
            case 1:
                openPage(BottomNavigationViewFragment.class);
                break;
            default:
                break;
        }
    }
}
