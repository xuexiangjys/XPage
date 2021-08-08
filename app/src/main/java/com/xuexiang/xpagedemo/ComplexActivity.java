package com.xuexiang.xpagedemo;

import android.os.Bundle;
import android.view.View;

import com.xuexiang.xpage.base.XPageActivity;
import com.xuexiang.xpagedemo.databinding.ActivityComplexBinding;
import com.xuexiang.xpagedemo.fragment.TabAFragment;
import com.xuexiang.xpagedemo.fragment.TabBFragment;
import com.xuexiang.xutil.tip.ToastUtils;

/**
 * @author xuexiang
 * @since 2018/5/30 下午5:41
 */
public class ComplexActivity extends XPageActivity implements View.OnClickListener {


    ActivityComplexBinding binding;

    @Override
    protected View getCustomRootView() {
        binding = ActivityComplexBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.btn1.setOnClickListener(this);
        binding.btn2.setOnClickListener(this);
        binding.btn3.setOnClickListener(this);
        binding.btn4.setOnClickListener(this);
        changePage(TabAFragment.class, null);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_1) {
            changePage(TabAFragment.class, null);
        } else if (id == R.id.btn_2) {
            changePage(TabBFragment.class, null);
        } else if (id == R.id.btn_3) {
            TabAFragment tabAFragment = getPage(TabAFragment.class);
            if (tabAFragment != null) {
                ToastUtils.toast(tabAFragment.getData());
            } else {
                ToastUtils.toast("页面还未加载！");
            }
        } else if (id == R.id.btn_4) {
            TabBFragment tabBFragment = getPage(TabBFragment.class);
            if (tabBFragment != null) {
                ToastUtils.toast(tabBFragment.getData());
            } else {
                ToastUtils.toast("页面还未加载！");
            }
        }
    }
}
