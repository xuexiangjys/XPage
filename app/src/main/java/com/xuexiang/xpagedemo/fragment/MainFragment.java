package com.xuexiang.xpagedemo.fragment;

import android.view.KeyEvent;
import android.view.View;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageContainerListFragment;
import com.xuexiang.xpage.core.PageOption;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xutil.common.ClickUtils;

/**
 * @author xuexiang
 * @date 2018/1/7 下午6:47
 */
@Page(name = "XPage", anim = CoreAnim.none)
public class MainFragment extends XPageContainerListFragment {

    /**
     * 获取页面的类集合[使用@Page注解进行注册的页面]
     *
     * @return
     */
    @Override
    protected Class[] getPagesClasses() {
        return new Class[]{
                DataTransmitFragment.class,
                AnimationFragment.class,
                ComplexPageFragment.class,
                PopBackFragment.class,
                PageOptionFragment.class,
                TestEditTextFragment.class
        };
    }

    @Override
    protected TitleBar initTitleBar() {
        return super.initTitleBar().setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickUtils.exitBy2Click();
            }
        });
    }

    @Override
    protected void onItemClick(int position) {
        if (position == 3) {
            PageOption.to(getSimpleDataItem(position))
                    .setNewActivity(true)
                    .setAnim(CoreAnim.slide)
                    .open(this);
        } else {
            super.onItemClick(position);
        }
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ClickUtils.exitBy2Click();
        }
        return true;
    }


}
