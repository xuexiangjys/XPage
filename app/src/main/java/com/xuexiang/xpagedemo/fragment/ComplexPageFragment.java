package com.xuexiang.xpagedemo.fragment;

import android.content.Intent;

import com.xuexiang.module_test.ModuleTestFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageSimpleListFragment;
import com.xuexiang.xpage.core.PageOption;
import com.xuexiang.xpagedemo.ComplexActivity;
import com.xuexiang.xpagedemo.activity.DisableBackKeyContainActivity;

import java.util.List;

/**
 * @author xuexiang
 * @since 2018/5/30 下午6:11
 */
@Page(name = "复杂使用")
public class ComplexPageFragment extends XPageSimpleListFragment {
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
        lists.add("加载module中的页面");
        lists.add("打开新的Activity，并且不加入返回堆栈，适合某些只能一步到头的业务");
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
                startActivity(new Intent(getContext(), ComplexActivity.class));
                break;
            case 1:
                openPage(BottomNavigationViewFragment.class);
                break;
            case 2:
                openPage(ModuleTestFragment.class);
                break;
            case 3:
                // 不仅可以传类名，还可以传标记符
                PageOption.to(StepFragment.class)
                        // 不加入堆栈中，即使用popToBack();无法返回上一个页面，适合某些只能一步到头的业务。
                        .setAddToBackStack(false)
                        .setNewActivity(true, DisableBackKeyContainActivity.class)
                        .open(this);
                break;
            default:
                break;
        }
    }

}
