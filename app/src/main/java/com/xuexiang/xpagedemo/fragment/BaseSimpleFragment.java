package com.xuexiang.xpagedemo.fragment;

import com.xuexiang.xpage.base.ListSimpleFragment;

import java.util.List;

/**
 * @author xuexiang
 * @date 2018/1/7 下午7:25
 */
public class BaseSimpleFragment extends ListSimpleFragment {
    /**
     * 初始化例子
     *
     * @param lists
     * @return
     */
    @Override
    protected List<String> initSimpleData(List<String> lists) {
        return getSimpleDatas(getSimpleDataClazzes());
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
}
