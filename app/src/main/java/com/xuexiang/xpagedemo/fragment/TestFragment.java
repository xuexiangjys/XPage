package com.xuexiang.xpagedemo.fragment;

import android.widget.TextView;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpagedemo.R;

import butterknife.BindView;

/**
 * @author xuexiang
 * @date 2018/1/7 下午11:27
 */
@Page(name = TestFragment.PAGE_NAME, extra = 123)
public class TestFragment extends XPageFragment {
    public final static String PAGE_NAME = "测试页面";
    @BindView(R.id.tv_content)
    TextView tvContent;

    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        tvContent.setText(getPageName());
    }

    /**
     * 初始化监听
     */
    @Override
    protected void initListeners() {

    }

}
