package com.xuexiang.module_test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuexiang.module_test.databinding.FragmentModuleTestBinding;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;

/**
 * @author xuexiang
 * @since 2021/1/10 1:55 AM
 */
@Page(name = "ModuleTest")
public class ModuleTestFragment extends XPageFragment {

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return FragmentModuleTestBinding.inflate(inflater, container, false).getRoot();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }
}
