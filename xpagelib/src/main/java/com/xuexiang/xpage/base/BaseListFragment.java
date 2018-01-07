package com.xuexiang.xpage.base;

import android.widget.AdapterView;
import android.widget.ListView;

import com.xuexiang.xpage.R;

/**
 * 带listview的基础fragment
 * @author XUE
 * @date 2017/9/10 23:16
 */
public abstract class BaseListFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    ListView mListView;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_listview;
    }

    @Override
    protected void initViews() {
        mListView = findView(R.id.lv_simple);
        mListView.setOnItemClickListener(this);
        initData();
    }

    protected abstract void initData();

    @Override
    protected void initListener() {

    }

    protected ListView getListView() {
        return mListView;
    }
}
