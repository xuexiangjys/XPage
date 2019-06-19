package com.xuexiang.xpagedemo.fragment;

import android.view.KeyEvent;
import android.widget.TextView;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.logger.PageLog;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xpagedemo.R;


import butterknife.BindView;

/**
 * @author xuexiang
 * @since 2018/6/3 下午10:22
 */
@Page(name = "TabC", anim = CoreAnim.slide)
public class TabCFragment extends XPageFragment {

    @BindView(R.id.tv_content)
    TextView tvContent;

    private int mData;

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

        mData = (int) (Math.random() * 1000);
    }

    /**
     * 初始化监听
     */
    @Override
    protected void initListeners() {

    }

    @Override
    public void onResume() {
        super.onResume();
        PageLog.d("TabCFragment:onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        PageLog.d("TabCFragment:onPause");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        PageLog.d("TabCFragment:onHiddenChanged:" + hidden);
    }


    public String getData() {
        return "这是TabC的数据:" + mData;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            popToBackInActivity();
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PageLog.d("TabCFragment:onDestroyView:");
    }
}
