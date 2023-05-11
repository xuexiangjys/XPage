package com.xuexiang.xpagedemo.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpagedemo.databinding.FragmentTestEdittextBinding;

/**
 * @author xuexiang
 * @since 2020/12/9 2:06 AM
 */
@Page(name = "测试输入框点击")
public class TestEditTextFragment extends XPageFragment {

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToRoot) {
        return FragmentTestEdittextBinding.inflate(inflater, container, attachToRoot).getRoot();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }

}
