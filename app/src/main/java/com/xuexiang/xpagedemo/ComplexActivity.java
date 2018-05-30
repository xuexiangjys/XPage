package com.xuexiang.xpagedemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xuexiang.xpage.base.XPageActivity;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpagedemo.fragment.DateReceiveFragment;
import com.xuexiang.xpagedemo.fragment.TestFragment;

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
        openPage(TestFragment.PAGE_NAME, null, CoreAnim.none);
    }


    @OnClick({R.id.btn_1, R.id.btn_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                changePage(TestFragment.PAGE_NAME, null, CoreAnim.none);
                break;
            case R.id.btn_2:
                Bundle params = new Bundle();
                params.putBoolean(DateReceiveFragment.KEY_IS_NEED_BACK, false);
                int id = (int) (Math.random() * 100);
                params.putString(DateReceiveFragment.KEY_EVENT_NAME, "事件" + id);
                params.putString(DateReceiveFragment.KEY_EVENT_DATA, "事件" + id + "携带的数据");
                changePage("数据接收", params, CoreAnim.none);
                break;
        }
    }
}
