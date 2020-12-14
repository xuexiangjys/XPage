package com.xuexiang.xpagedemo.fragment;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpagedemo.R;

/**
 * @author xuexiang
 * @since 2020/12/9 2:06 AM
 */
@Page(name = "测试输入框点击")
public class TestEditTextFragment extends XPageFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_edittext;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }

}
