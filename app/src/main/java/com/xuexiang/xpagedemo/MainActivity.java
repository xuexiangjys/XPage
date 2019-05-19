package com.xuexiang.xpagedemo;

import android.os.Bundle;

import com.xuexiang.xpage.base.XPageActivity;
import com.xuexiang.xpage.core.PageOption;
import com.xuexiang.xpagedemo.fragment.MainFragment;

public class MainActivity extends XPageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        openPage(MainFragment.class);
        PageOption.to(MainFragment.class).open(this);
    }
}
