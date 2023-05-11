package com.xuexiang.xpage.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xuexiang.xpage.R;

/**
 * 带ListView的基础fragment
 *
 * @author xuexiang
 * @since 2018/5/24 下午3:36
 */
public abstract class XPageListFragment extends XPageFragment implements AdapterView.OnItemClickListener {

    protected ListView mListView;

    // 不使用模板
    @Nullable
    @Override
    protected View onCreateRootView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return null;
    }

    // 直接加载布局
    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return inflater.inflate(R.layout.xpage_fragment_listview, container, attachToRoot);
    }

    @Override
    protected void initViews() {
        mListView = findViewById(R.id.lv_simple);
        mListView.setOnItemClickListener(this);
        initData();
    }

    /**
     * 初始化集合数据
     */
    protected abstract void initData();

    @Override
    protected void initListeners() {

    }

    protected ListView getListView() {
        return mListView;
    }

    @Override
    public void onDestroyView() {
        if (mListView != null) {
            mListView.setOnItemClickListener(null);
        }
        super.onDestroyView();
    }
}
