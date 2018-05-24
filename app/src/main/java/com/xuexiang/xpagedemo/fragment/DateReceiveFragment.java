package com.xuexiang.xpagedemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpagedemo.R;

import butterknife.BindView;
import butterknife.OnClick;

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

    @BindView(R.id.tv_display)
    TextView mTvDisplay;
    @BindView(R.id.btn_back)
    Button mBtnBack;
    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_datereceive;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTvDisplay.setText("接收到数据\n 事件名：" + arguments.getString(KEY_EVENT_NAME) + "\n 事件数据：" + arguments.getString(KEY_EVENT_DATA));
            boolean isNeedBack = arguments.getBoolean(KEY_IS_NEED_BACK);
            if (isNeedBack) {
                mBtnBack.setVisibility(View.VISIBLE);
            } else {
                mBtnBack.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.btn_back)
    void back(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_BACK_DATA, "==【返回的数据】==");
        intent.putExtras(bundle);
        setFragmentResult(500, intent);
        popToBack();
    }

    /**
     * 初始化监听
     */
    @Override
    protected void initListeners() {

    }
}
