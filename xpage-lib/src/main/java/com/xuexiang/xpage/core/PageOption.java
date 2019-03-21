package com.xuexiang.xpage.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.xuexiang.xpage.PageConfig;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.model.PageInfo;

import java.util.Arrays;

import static com.xuexiang.xpage.core.CoreSwitchBean.convertAnimations;

/**
 * 页面选项
 *
 * @author XUE
 * @since 2019/3/21 10:08
 */
public class PageOption {
    /**
     * 页面名
     */
    private String mPageName;
    /**
     * 相关数据
     */
    private Bundle mBundle = null;
    /**
     * 动画类型
     */
    private int[] mAnim = null;
    /**
     * 是否添加到栈中
     */
    private boolean mAddToBackStack = true;
    /**
     * 是否起新的Activity
     */
    private boolean mNewActivity = false;
    /**
     * 请求code码
     */
    private int mRequestCode = -1;

    public PageOption(String pageName) {
        mPageName = pageName;
    }

    public <T extends XPageFragment> PageOption(Class<T> clazz) {
        PageInfo pageInfo = PageConfig.getPageInfo(clazz);
        mPageName = pageInfo.getName();
        setAnim(pageInfo.getAnim());
    }

    public PageOption(String pageName, Bundle bundle) {
        mPageName = pageName;
        mBundle = bundle;
    }

    public PageOption(String pageName, Bundle bundle, boolean addToBackStack) {
        mPageName = pageName;
        mBundle = bundle;
        mAddToBackStack = addToBackStack;
    }

    public PageOption(String pageName, Bundle bundle, int[] anim, boolean addToBackStack, boolean newActivity, int requestCode) {
        mPageName = pageName;
        mBundle = bundle;
        setAnim(anim);
        mAddToBackStack = addToBackStack;
        mNewActivity = newActivity;
        mRequestCode = requestCode;
    }

    public PageOption(String pageName, Bundle bundle, CoreAnim anim, boolean addToBackStack, boolean newActivity, int requestCode) {
        mPageName = pageName;
        mBundle = bundle;
        setAnim(anim);
        mAddToBackStack = addToBackStack;
        mNewActivity = newActivity;
        mRequestCode = requestCode;
    }

    public String getPageName() {
        return mPageName;
    }

    public PageOption setPageName(String pageName) {
        mPageName = pageName;
        return this;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public PageOption setBundle(Bundle bundle) {
        mBundle = bundle;
        return this;
    }

    public PageOption putString(@Nullable String key, @Nullable String value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putString(key, value);
        return this;
    }

    public PageOption putBoolean(@Nullable String key, boolean value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putBoolean(key, value);
        return this;
    }

    public PageOption putInt(@Nullable String key, int value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putInt(key, value);
        return this;
    }

    public PageOption putFloat(@Nullable String key, float value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putFloat(key, value);
        return this;
    }

    public PageOption putDouble(@Nullable String key, double value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putDouble(key, value);
        return this;
    }

    public int[] getAnim() {
        return mAnim;
    }

    public PageOption setAnim(int[] anim) {
        mAnim = anim;
        return this;
    }

    public PageOption setAnim(CoreAnim anim) {
        mAnim = convertAnimations(anim);
        return this;
    }

    public boolean isAddToBackStack() {
        return mAddToBackStack;
    }

    public PageOption setAddToBackStack(boolean addToBackStack) {
        mAddToBackStack = addToBackStack;
        return this;
    }

    public boolean isNewActivity() {
        return mNewActivity;
    }

    public PageOption setNewActivity(boolean newActivity) {
        mNewActivity = newActivity;
        return this;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public boolean isOpenForResult() {
        return mRequestCode != -1;
    }

    /**
     * StartForResult
     *
     * @param requestCode
     * @return
     */
    public PageOption setRequestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    public Fragment open(@NonNull XPageFragment fragment) {
        return fragment.openPage(this);
    }

    public Fragment openForResult(@NonNull XPageFragment fragment, int requestCode) {
        setRequestCode(requestCode);
        return fragment.openPage(this);
    }

    @Override
    public String toString() {
        return "PageOption{" +
                "mPageName='" + mPageName + '\'' +
                ", mBundle=" + mBundle +
                ", mAnim=" + Arrays.toString(mAnim) +
                ", mAddToBackStack=" + mAddToBackStack +
                ", mNewActivity=" + mNewActivity +
                ", mRequestCode=" + mRequestCode +
                '}';
    }
}
