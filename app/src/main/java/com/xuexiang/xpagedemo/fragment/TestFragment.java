package com.xuexiang.xpagedemo.fragment;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.BaseFragment;
import com.xuexiang.xpagedemo.R;

/**
 * @author xuexiang
 * @date 2018/1/7 下午11:27
 */
@Page(name = TestFragment.PAGE_NAME)
public class TestFragment extends BaseFragment {
    public final static String PAGE_NAME = "测试页面";
    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

    }

    /**
     * 初始化监听
     */
    @Override
    protected void initListener() {

    }
}
