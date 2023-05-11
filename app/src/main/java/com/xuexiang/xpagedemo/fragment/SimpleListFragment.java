package com.xuexiang.xpagedemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpage.core.PageOption;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xpagedemo.databinding.FragmentSimpleBinding;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;

/**
 * 注意，这个页面并没有使用@Page进行标记，并不能使用XPage路由打开！！
 *
 * @author xuexiang
 * @since 2019-06-19 14:37
 */
public class SimpleListFragment extends XPageFragment {

    private static final String KEY_TITLE = "key_title";

    FragmentSimpleBinding binding;

    public static SimpleListFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        SimpleListFragment fragment = new SimpleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @AutoWired(name = KEY_TITLE)
    String title;

    @Override
    protected TitleBar initTitleBar() {
        //不使用@Page标注的一定要注意覆盖这个方法
        return null;
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToRoot) {
        binding = FragmentSimpleBinding.inflate(inflater, container, attachToRoot);
        return binding.getRoot();
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        XRouter.getInstance().inject(this);
    }

    @Override
    protected void initViews() {
        binding.tvContent.setText(String.format("这个是%s页面！！！", title));
    }

    @Override
    protected void initListeners() {
        binding.tvTest.setOnClickListener(v -> PageOption.to(TestFragment.class)
                //新建一个容器，以不影响当前容器
                .setNewActivity(true)
                .open(SimpleListFragment.this));
    }

}
