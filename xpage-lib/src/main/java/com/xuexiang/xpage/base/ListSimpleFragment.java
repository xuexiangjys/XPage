package com.xuexiang.xpage.base;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.xuexiang.xpage.PageConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的ListView演示fragment
 * @author XUE
 * @date 2017/9/10 23:30
 */
public abstract class ListSimpleFragment extends BaseListFragment {
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

    protected List<String> getSimpleDatas(Class... clazzes) {
        List<String> simpleDatalist = new ArrayList<>();
        if (clazzes != null && clazzes.length > 0) {
            for (int i = 0; i < clazzes.length; i ++) {
                simpleDatalist.add(PageConfig.getPageInfo(clazzes[i]).getName());
            }
        }
        return simpleDatalist;
    }

    /**
     * 获取演示demo的类集合
     * @return
     */
    public Class[] getSimpleDataClazzes() {
        return null;
    }
}
