package com.xuexiang.xpagedemo.activity;

import android.view.KeyEvent;

import com.xuexiang.xpage.base.XPageActivity;

/**
 * 自定义的容器Activity, 禁止返回键
 *
 * @author xuexiang
 * @since 2019-05-19 22:28
 */
public class DisableBackKeyContainActivity extends XPageActivity {

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 禁止返回键
        return onDisableBackKeyDown(keyCode) && super.onKeyDown(keyCode, event);
    }

    public static boolean onDisableBackKeyDown(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_HOME:
                return false;
            default:
                break;
        }
        return true;
    }

}
