package com.xuexiang.xpagedemo;

import android.os.Bundle;

import com.xuexiang.xpage.base.BaseActivity;
import com.xuexiang.xpagedemo.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openPage(MainFragment.class);

    }
}
