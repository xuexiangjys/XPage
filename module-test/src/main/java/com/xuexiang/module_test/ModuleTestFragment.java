package com.xuexiang.module_test;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;

/**
 * @author xuexiang
 * @since 2021/1/10 1:55 AM
 */
@Page(name = "ModuleTest")
public class ModuleTestFragment extends XPageFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_module_test;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }
}
