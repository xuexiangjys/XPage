package com.xuexiang.xpagedemo.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageSimpleListFragment;
import com.xuexiang.xpage.core.PageOption;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpagedemo.activity.ContainActivity;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.List;

import static com.xuexiang.xpagedemo.fragment.TestFragment.KEY_POP_BACK_NAME;

/**
 * @author XUE
 * @since 2019/3/21 10:56
 */
@Page(name = "PageOption使用")
public class PageOptionFragment extends XPageSimpleListFragment {

    /**
     * 初始化例子
     *
     * @param lists
     * @return
     */
    @Override
    protected List<String> initSimpleData(List<String> lists) {
        lists.add("普通打开");
        lists.add("设置动画");
        lists.add("是否支持数据返回");
        lists.add("打开新的Activity");
        lists.add("使用自定义的容器打开新的Activity");
        lists.add("返回至指定的页面");
        return lists;
    }

    /**
     * 条目点击
     *
     * @param position
     */
    @Override
    protected void onItemClick(int position) {
        PageOption pageOption = new PageOption(TestFragment.class);
        switch (position) {
            case 0:
                pageOption.setTargetPage(AnimationFragment.class);
                break;
            case 1:
                pageOption.setAnim(CoreAnim.zoom);
                break;
            case 2:
                pageOption.setRequestCode(100)
                        .putBoolean(DateReceiveFragment.KEY_IS_NEED_BACK, true);
                break;
            case 3:
                pageOption.setNewActivity(true);
                break;
            case 4:
                pageOption.setNewActivity(true, ContainActivity.class);
                break;
            case 5:
                //下个页面点击返回后，直接返回至XPage主页面
                pageOption.putString(KEY_POP_BACK_NAME, "XPage");
                break;
            default:
                break;
        }
        pageOption.open(this);
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
