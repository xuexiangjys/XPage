package com.xuexiang.xpage.base;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的ListView演示fragment
 *
 * @author xuexiang
 * @since 2018/5/24 下午3:39
 */
public abstract class XPageSimpleListFragment extends XPageListFragment {

    protected List<String> mSimpleData = new ArrayList<>();

    @Override
    protected void initData() {
        mSimpleData = initSimpleData(mSimpleData);
        getListView().setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, mSimpleData));
        initSimply();
    }

    /**
     * 数据初始化后,简单地进行其他的初始化操作
     */
    protected void initSimply() {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        onItemClick(position);
    }

    /**
     * 初始化例子
     *
     * @param lists 例子数据
     * @return 例子数据
     */
    protected abstract List<String> initSimpleData(List<String> lists);

    /**
     * 条目点击
     *
     * @param position 点击的条目索引
     */
    protected abstract void onItemClick(int position);

    @Override
    public void onDestroyView() {
        if (mSimpleData != null && mSimpleData.size() > 0) {
            mSimpleData.clear();
        }
        super.onDestroyView();
    }

    protected String getSimpleDataItem(int position) {
        return mSimpleData.get(position);
    }

    /**
     * 获取页面类的集合
     *
     * @return 页面类的集合
     */
    public Class[] getSimplePageClasses() {
        return new Class[0];
    }

}
