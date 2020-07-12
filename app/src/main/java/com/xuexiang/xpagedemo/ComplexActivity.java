package com.xuexiang.xpagedemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xuexiang.xpage.base.XPageActivity;
import com.xuexiang.xpagedemo.fragment.TabAFragment;
import com.xuexiang.xpagedemo.fragment.TabBFragment;
import com.xuexiang.xutil.tip.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xuexiang
 * @since 2018/5/30 下午5:41
 */
public class ComplexActivity extends XPageActivity {

    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_complex;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        changePage(TabAFragment.class, null);
    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                changePage(TabAFragment.class, null);
                break;
            case R.id.btn_2:
                changePage(TabBFragment.class, null);
                break;
            case R.id.btn_3:
                TabAFragment tabAFragment = getPage(TabAFragment.class);
                if (tabAFragment != null) {
                    ToastUtils.toast(tabAFragment.getData());
                } else {
                    ToastUtils.toast("页面还未加载！");
                }
                break;
            case R.id.btn_4:
                TabBFragment tabBFragment = getPage(TabBFragment.class);
                if (tabBFragment != null) {
                    ToastUtils.toast(tabBFragment.getData());
                } else {
                    ToastUtils.toast("页面还未加载！");
                }
                break;
            default:
                break;
        }
    }
}
