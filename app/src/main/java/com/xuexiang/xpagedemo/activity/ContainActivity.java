package com.xuexiang.xpagedemo.activity;

import android.os.Bundle;

import com.xuexiang.xpage.base.XPageActivity;
import com.xuexiang.xutil.tip.ToastUtils;

/**
 * 自定义的容器Activity
 *
 * @author xuexiang
 * @since 2019-05-19 22:28
 */
public class ContainActivity extends XPageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastUtils.toast("自定义容器Activity");
    }
}
