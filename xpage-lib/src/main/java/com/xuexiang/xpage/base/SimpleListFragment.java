package com.xuexiang.xpage.base;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的ListView演示fragment
 * @author XUE
 * @date 2017/9/10 23:30
 */
public abstract class SimpleListFragment extends BaseListFragment {
    protected List<String> mSimpleData = new ArrayList<>();

    @Override
    protected void initData() {
        mSimpleData = initSimpleData(mSimpleData);
        getListView().setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, mSimpleData));
        init();
    }

    /**
     * 初始化
     */
    protected void init() {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        onItemClick(position);
    }

    /**
     * 初始化例子
     *
     * @return
     */
    protected abstract List<String> initSimpleData(List<String> lists);

    /**
     * 条目点击
     *
     * @param position
     */
    protected abstract void onItemClick(int position);

    @Override
    public void onDestroy() {
        if (mSimpleData != null && mSimpleData.size() > 0) {
            mSimpleData.clear();
        }
        super.onDestroy();
    }

    protected String getSimpleDataItem(int position) {
        return mSimpleData.get(position);
    }

    /**
     * 获取页面类的集合
     * @return
     */
    public Class[] getSimplePageClasses() {
        return new Class[0];
    }

}
