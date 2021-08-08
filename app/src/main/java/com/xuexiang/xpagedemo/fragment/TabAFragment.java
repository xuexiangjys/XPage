package com.xuexiang.xpagedemo.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.logger.PageLog;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xpagedemo.databinding.FragmentTestBinding;

/**
 * @author xuexiang
 * @since 2018/6/3 下午10:13
 */
@Page(name = "TabA", anim = CoreAnim.none)
public class TabAFragment extends XPageFragment {

    FragmentTestBinding binding;

    private int mData;

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentTestBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        binding.tvContent.setText("这里是 " + getPageName() + " 页面");
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
        PageLog.d("TabAFragment:onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        PageLog.d("TabAFragment:onPause");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        PageLog.d("TabAFragment:onHiddenChanged:" + hidden);
    }

    public String getData() {
        return "这是TabA的数据:" + mData;
    }
}
