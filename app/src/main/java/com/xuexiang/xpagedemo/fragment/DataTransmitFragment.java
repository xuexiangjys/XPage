package com.xuexiang.xpagedemo.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageSimpleListFragment;
import com.xuexiang.xpage.core.PageOption;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.List;

/**
 * @author xuexiang
 * @date 2018/1/7 下午9:47
 */
@Page(name = "数据传递")
public class DataTransmitFragment extends XPageSimpleListFragment {
    /**
     * 初始化例子
     *
     * @param lists
     * @return
     */
    @Override
    protected List<String> initSimpleData(List<String> lists) {
        lists.add("传递数据<-->不返回数据");
        lists.add("传递数据<-->返回数据");
        return lists;
    }

    /**
     * 条目点击
     *
     * @param position
     */
    @Override
    protected void onItemClick(int position) {
        Bundle params = new Bundle();
        switch(position) {
            case 0:
                params.putBoolean(DateReceiveFragment.KEY_IS_NEED_BACK, false);
                int id = (int) (Math.random() * 100);
                params.putString(DateReceiveFragment.KEY_EVENT_NAME, "事件" + id);
                params.putString(DateReceiveFragment.KEY_EVENT_DATA, "事件" + id + "携带的数据");
                openPage(DateReceiveFragment.class, params);
                break;
            case 1:
                PageOption.to(DateReceiveFragment.class)
                        .putBoolean(DateReceiveFragment.KEY_IS_NEED_BACK, true)
                        .setRequestCode(100)
                        .open(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle extras = data.getExtras();
            ToastUtils.toast("requestCode:" + requestCode + " resultCode:" + resultCode + " data:" + extras.getString(DateReceiveFragment.KEY_BACK_DATA));
        }
    }
}
