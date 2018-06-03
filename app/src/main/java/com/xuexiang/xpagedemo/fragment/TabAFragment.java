package com.xuexiang.xpagedemo.fragment;

import android.widget.TextView;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xpagedemo.R;

import butterknife.BindView;

/**
 * @author xuexiang
 * @since 2018/6/3 下午10:13
 */
@Page(name = "TabA", anim = CoreAnim.none)
public class TabAFragment extends XPageFragment {

    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

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
        tvContent.setText("这里是 " + getPageName() + " 页面");
    }

    /**
     * 初始化监听
     */
    @Override
    protected void initListeners() {

    }

    public String getData() {
        return "这是TabA的数据";
    }
}
