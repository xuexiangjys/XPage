package com.xuexiang.module_test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToRoot) {
        return FragmentModuleTestBinding.inflate(inflater, container, attachToRoot).getRoot();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }
}
