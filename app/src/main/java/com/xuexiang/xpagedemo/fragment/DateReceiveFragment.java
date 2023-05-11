package com.xuexiang.xpagedemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xpagedemo.databinding.FragmentDatereceiveBinding;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;

/**
 * @author xuexiang
 * @date 2018/1/7 下午10:05
 */
@Page(name = "数据接收", params = {DateReceiveFragment.KEY_IS_NEED_BACK, DateReceiveFragment.KEY_EVENT_NAME, DateReceiveFragment.KEY_EVENT_DATA})
public class DateReceiveFragment extends XPageFragment {
    public final static String KEY_IS_NEED_BACK = "is_need_back";
    public final static String KEY_EVENT_NAME = "event_name";
    public final static String KEY_EVENT_DATA = "event_data";
    public final static String KEY_BACK_DATA = "back_data";

    FragmentDatereceiveBinding binding;

    @AutoWired(name = KEY_IS_NEED_BACK)
    boolean isNeedBack;
    @AutoWired(name = KEY_EVENT_NAME)
    String eventName;
    @AutoWired(name = KEY_EVENT_DATA)
    String eventData;


    int Number = 1000;

    boolean isChanged;

    @Override
    protected TitleBar initTitleBar() {
        TitleBar titleBar = super.initTitleBar();
        titleBar.addAction(new TitleBar.TextAction("变化") {
            @Override
            public void performAction(View view) {
                Number = (int) (Math.random() * 1000);
                update();
            }
        });
        return titleBar;
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToRoot) {
        binding = FragmentDatereceiveBinding.inflate(inflater, container, attachToRoot);
        return binding.getRoot();
    }

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        update();
        if (isNeedBack) {
            binding.btnBack.setVisibility(View.VISIBLE);
        } else {
            binding.btnBack.setVisibility(View.GONE);
        }
    }

    private void update() {
        binding.tvDisplay.setText(String.format("接收到数据\n 事件名：%s\n 事件数据：%s\n 数字：%d", eventName, eventData, Number));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        isChanged = true;
    }

    /**
     * 初始化监听
     */
    @Override
    protected void initListeners() {
        binding.btnBack.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(KEY_BACK_DATA, "==【返回的数据】==");
            setFragmentResult(500, intent);
            popToBack();
        });
    }
}
