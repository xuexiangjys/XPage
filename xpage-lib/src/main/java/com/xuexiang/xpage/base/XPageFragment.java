package com.xuexiang.xpage.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;
import com.xuexiang.xpage.PageConfig;
import com.xuexiang.xpage.core.CoreSwitchBean;
import com.xuexiang.xpage.core.CoreSwitcher;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.logger.PageLog;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xpage.utils.TitleUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 全局基类BaseFragment
 *
 * @author xuexiang
 * @since 2018/5/24 下午3:49
 */
public abstract class XPageFragment extends Fragment {
    /**
     * 所在activity
     */
    protected Activity mActivity;
    /**
     * 页面名
     */
    private String mPageName;
    /**
     * 用于startForResult的requestCode
     */
    private int mRequestCode;
    /**
     * openPageForResult接口，用于传递返回结果
     */
    private CoreSwitcher mPageCoreSwitcher;

    private OnFragmentFinishListener mFragmentFinishListener;

    private View mRootView;
    private Unbinder mUnbinder;

    /**
     * 设置该接口用于返回结果
     *
     * @param listener OnFragmentFinishListener对象
     */
    public void setFragmentFinishListener(OnFragmentFinishListener listener) {
        mFragmentFinishListener = listener;
    }

    /**
     * 设置openPageForResult打开的页面的返回结果
     *
     * @param resultCode 返回结果码
     * @param intent     返回的intent对象
     */
    public void setFragmentResult(int resultCode, Intent intent) {
        if (mFragmentFinishListener != null) {
            mFragmentFinishListener.onFragmentResult(mRequestCode, resultCode, intent);
        }
    }

    /**
     * 得到requestCode
     *
     * @return 请求码
     */
    public int getRequestCode() {
        return mRequestCode;
    }

    /**
     * 设置requestCode
     *
     * @param code 请求码
     */
    public void setRequestCode(int code) {
        mRequestCode = code;
    }

    /**
     * 将Activity中onKeyDown在Fragment中实现，
     *
     * @param keyCode keyCode码
     * @param event   KeyEvent对象
     * @return 是否处理
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }


    //================页面返回===================//
    /**
     * 数据设置，回调
     *
     * @param bundle 刷新数据的Bundle对象
     */
    public void onFragmentDataReset(Bundle bundle) {

    }


    public interface PopCallback {
        void run();
    }

    /**
     * 用于openPageForResult，获得返回内容后需要再次调openPage的场景，只适合不新开Activity的情况，如果新开activity请像Activity返回结果那样操作
     *
     * @param callback 返回码
     */
    public void popToBackForResult(PopCallback callback) {
        this.popToBack(null, null);
        callback.run();
    }

    /**
     * 弹出栈顶的Fragment。如果Activity中只有一个Fragemnt时，Acitivity也退出。
     */
    public void popToBack() {
        popToBack(null, null);
    }

    /**
     * 如果在fragment栈中找到，则跳转到该fragment中去，否则弹出栈顶
     *
     * @param pageName 页面名
     * @param bundle   参数
     */
    public final void popToBack(String pageName, Bundle bundle) {
        CoreSwitcher coreSwitcher = getSwitcher();
        if (coreSwitcher != null) {
            if (pageName == null) {
                coreSwitcher.popPage();
            } else {
                if (this.findPage(pageName)) {
                    CoreSwitchBean page = new CoreSwitchBean(pageName, bundle);
                    coreSwitcher.gotoPage(page);
                } else {
                    coreSwitcher.popPage();
                }
            }
        } else {
            PageLog.d("pageSwitcher null");
        }
    }

    /**
     * 得到页面切换Switcher
     *
     * @return 页面切换Switcher
     */
    public CoreSwitcher getSwitcher() {
        synchronized (XPageFragment.this) {// 加强保护，保证pageSwitcher 不为null
            if (mPageCoreSwitcher == null) {
                if (this.mActivity != null && this.mActivity instanceof CoreSwitcher) {
                    mPageCoreSwitcher = (CoreSwitcher) this.mActivity;
                }
                if (mPageCoreSwitcher == null) {
                    XPageActivity topActivity = XPageActivity.getTopActivity();
                    if (topActivity != null) {
                        mPageCoreSwitcher = topActivity;
                    }
                }
            }
        }
        return mPageCoreSwitcher;
    }

    /**
     * 设置Switcher
     *
     * @param pageCoreSwitcher CoreSwitcher对象
     */
    public XPageFragment setSwitcher(CoreSwitcher pageCoreSwitcher) {
        mPageCoreSwitcher = pageCoreSwitcher;
        return this;
    }

    /**
     * 查找fragment是否存在，通过Switcher查找
     *
     * @param pageName 页面名
     * @return 是否找到
     */
    public boolean findPage(String pageName) {
        if (pageName == null) {
            PageLog.d("pageName is null");
            return false;
        }
        CoreSwitcher coreSwitcher = getSwitcher();
        if (coreSwitcher != null) {
            return coreSwitcher.findPage(pageName);
        } else {
            PageLog.d("pageSwitch is null");
            return false;
        }

    }

    /**
     * 对应fragment是否位于栈顶，通过Switcher查找
     *
     * @param fragmentTag fragment的tag
     * @return 是否位于栈顶
     */
    public boolean isFragmentTop(String fragmentTag) {
        CoreSwitcher pageCoreSwitcher = this.getSwitcher();
        if (pageCoreSwitcher != null) {
            return pageCoreSwitcher.isFragmentTop(fragmentTag);

        } else {
            PageLog.d("pageSwitcher is null");
            return false;
        }
    }

    /**
     * 重新该方法用于获得返回的数据
     *
     * @param requestCode 请求码
     * @param resultCode  返回结果码
     * @param data        返回数据
     */
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        PageLog.d("onFragmentResult from baseFragment：requestCode-" + requestCode + "  resultCode-" + resultCode);
    }

    //====================openPage=========================//

    /**
     * 打开fragment[使用注解反射]
     *
     * @return 打开的fragment对象
     */
    public Fragment openPage(Class<?> clazz) {
        return openPage(PageConfig.getPageInfo(clazz).getName(), null, PageConfig.getPageInfo(clazz).getAnim());
    }

    /**
     * 打开fragment[使用注解反射]
     *
     * @param clazz  页面类
     * @param bundle 页面跳转时传递的参数
     * @return 打开的fragment对象
     */
    public Fragment openPage(Class<?> clazz, Bundle bundle) {
        return openPage(PageConfig.getPageInfo(clazz).getName(), bundle, PageConfig.getPageInfo(clazz).getAnim());
    }

    /**
     * 打开fragment[使用注解反射]
     *
     * @param clazz  页面类
     * @param bundle 页面跳转时传递的参数
     * @return 打开的fragment对象
     */
    public Fragment openPage(Class<?> clazz, Bundle bundle, CoreAnim coreAnim) {
        return openPage(PageConfig.getPageInfo(clazz).getName(), bundle, coreAnim);
    }

    /**
     * 打开fragment
     *
     * @param pageName 页面名称
     * @return 打开的fragment对象
     */
    public Fragment openPage(String pageName) {
        return openPage(pageName, null, CoreAnim.slide);
    }


    /**
     * 打开fragment
     *
     * @param pageName 页面名称
     * @param bundle   页面跳转时传递的参数
     * @return 打开的fragment对象
     */
    public Fragment openPage(String pageName, Bundle bundle) {
        return openPage(pageName, bundle, CoreAnim.slide);
    }

    /**
     * 在当前activity中打开一个fragment，并添加到返回栈中
     *
     * @param pageName Fragment 名，在page.json中配置。
     * @param bundle   页面跳转时传递的参数
     * @param coreAnim 指定的动画理性 none/slide(左右平移)/present(由下向上)/fade(fade 动画)
     * @return 打开的fragment对象
     */
    public final Fragment openPage(String pageName, Bundle bundle, CoreAnim coreAnim) {
        return openPage(pageName, bundle, CoreSwitchBean.convertAnimations(coreAnim), true);
    }

    /**
     * 在当前activity中打开一个fragment，并设置是否添加到返回栈
     *
     * @param pageName       Fragemnt 名，在page.json中配置。
     * @param bundle         页面跳转时传递的参数
     * @param anim           指定的动画理性 none/slide(左右平移)/present(由下向上)/fade(fade 动画)
     * @param addToBackStack 是否添加到用户操作栈中
     * @return 打开的fragment对象
     */
    public final Fragment openPage(String pageName, Bundle bundle, int[] anim, boolean addToBackStack) {
        return openPage(pageName, bundle, anim, addToBackStack, false);
    }

    /**
     * 打开一个fragment并设置是否新开activity，设置是否添加返回栈
     *
     * @param pageName       Fragemnt 名，在page.json中配置。
     * @param bundle         页面跳转时传递的参数
     * @param anim           指定的动画理性 none/slide(左右平移)/present(由下向上)/fade(fade 动画)
     * @param addToBackStack 是否添加到用户操作栈中
     * @param newActivity    该页面是否新建一个Activity
     * @return 打开的fragment对象
     */
    public final Fragment openPage(String pageName, Bundle bundle, int[] anim, boolean addToBackStack, boolean newActivity) {
        if (pageName == null) {
            PageLog.d("pageName is null");
            return null;
        }
        CoreSwitcher coreSwitcher = this.getSwitcher();
        if (coreSwitcher != null) {
            CoreSwitchBean page = new CoreSwitchBean(pageName, bundle, anim, addToBackStack, newActivity);
            return coreSwitcher.openPage(page);
        } else {
            PageLog.d("pageSwitcher is null");
            return null;
        }
    }

    /**
     * 在当前activity中打开一个fragment，并添加到返回栈中
     *
     * @param pageName Fragemnt 名，在page.json中配置。
     * @param bundle   页面跳转时传递的参数
     * @param anim     指定的动画理性 none/slide(左右平移)/present(由下向上)/fade(fade 动画)
     * @return 打开的fragment对象
     */
    public final Fragment openPage(String pageName, Bundle bundle, int[] anim) {
        return openPage(pageName, bundle, anim, true);
    }

    /**
     * 在当前activity中打开一个fragment，并设置是否添加到返回栈
     *
     * @param pageName       Fragemnt 名，在page.json中配置。
     * @param bundle         页面跳转时传递的参数
     * @param coreAnim       指定的动画理性 none/slide(左右平移)/present(由下向上)/fade(fade 动画)
     * @param addToBackStack 是否添加到用户操作栈中
     * @return 打开的fragment对象
     */
    public final Fragment openPage(String pageName, Bundle bundle, CoreAnim coreAnim, boolean addToBackStack) {
        return openPage(pageName, bundle, CoreSwitchBean.convertAnimations(coreAnim), addToBackStack, false);
    }

    /**
     * 打开一个fragment并设置是否新开activity，设置是否添加返回栈
     *
     * @param pageName       Fragemnt 名，在page.json中配置。
     * @param bundle         页面跳转时传递的参数
     * @param coreAnim       指定的动画理性 none/slide(左右平移)/present(由下向上)/fade(fade 动画)
     * @param addToBackStack 是否添加到用户操作栈中
     * @param newActivity    该页面是否新建一个Activity
     * @return 打开的fragment对象
     */
    public final Fragment openPage(String pageName, Bundle bundle, CoreAnim coreAnim, boolean addToBackStack, boolean newActivity) {
        return openPage(pageName, bundle, CoreSwitchBean.convertAnimations(coreAnim), addToBackStack, newActivity);
    }

    /**
     * @param pageName 页面名
     * @param bundle   参数
     * @param coreAnim 动画
     * @return 打开的fragment对象
     */
    public Fragment gotoPage(String pageName, Bundle bundle, CoreAnim coreAnim) {
        return gotoPage(pageName, bundle, coreAnim, false);

    }

    /**
     * 新建或跳转到一个页面（Fragment）。找不到pageName Fragment时，就新建Fragment。找到pageName
     * Fragment时,则弹出该Fragement到栈顶上的所有actvity和fragment
     *
     * @param pageName    Fragemnt 名，在在page.json中配置。
     * @param bundle      页面跳转时传递的参数
     * @param coreAnim    指定的动画理性 none/slide(左右平移)/present(由下向上)/fade(fade 动画)
     * @param newActivity 该页面是否新建一个Activity
     * @return 打开的fragment对象
     */
    public Fragment gotoPage(String pageName, Bundle bundle, CoreAnim coreAnim, boolean newActivity) {
        CoreSwitcher pageCoreSwitcher = this.getSwitcher();
        if (pageCoreSwitcher != null) {
            CoreSwitchBean page = new CoreSwitchBean(pageName, bundle, coreAnim, true, newActivity);
            return pageCoreSwitcher.gotoPage(page);
        } else {
            PageLog.d("pageSwitcher is null");
            return null;
        }
    }

    /**
     * 打开fragment并请求获得返回值
     *
     * @param clazz       页面类
     * @param bundle      参数
     * @param requestCode 请求码
     * @return 打开的fragment对象
     */
    public final Fragment openPageForResult(Class<?> clazz, Bundle bundle, int requestCode) {
        return openPageForResult(false, PageConfig.getPageInfo(clazz).getName(), bundle, PageConfig.getPageInfo(clazz).getAnim(), requestCode);
    }

    /**
     * 打开fragment并请求获得返回值
     *
     * @param pageName    页面名
     * @param bundle      参数
     * @param coreAnim    动画
     * @param requestCode 请求码
     * @return 打开的fragment对象
     */
    public final Fragment openPageForResult(String pageName, Bundle bundle, CoreAnim coreAnim, int requestCode) {
        return openPageForResult(false, pageName, bundle, coreAnim, requestCode);
    }

    /**
     * 打开fragment并请求获得返回值,并设置是否在新activity中打开
     *
     * @param newActivity 是否新开activity
     * @param pageName    页面名
     * @param bundle      参数
     * @param coreAnim    动画
     * @param requestCode 请求码
     * @return 打开的fragment对象
     */
    public final Fragment openPageForResult(boolean newActivity, String pageName, Bundle bundle, CoreAnim coreAnim, int requestCode) {
        CoreSwitcher pageCoreSwitcher = this.getSwitcher();
        if (pageCoreSwitcher != null) {
            CoreSwitchBean page = new CoreSwitchBean(pageName, bundle, coreAnim, true, newActivity);
            page.setRequestCode(requestCode);

            return pageCoreSwitcher.openPageForResult(page, this);
        } else {
            PageLog.d("pageSwitcher is null");
            return null;
        }
    }

    //======================生命周期=======================//
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getPageName() != null) {
            PageLog.d("====Fragment.onCreate====" + getPageName());
        }
    }

    /**
     * 布局的资源id
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutId();


    /**
     * 获取页面标题
     */
    protected String getPageTitle() {
        return PageConfig.getPageInfo(getClass()).getName();
    }

    /**
     * 初始化参数
     */
    protected void initArgs() {

    }

    /**
     * 初始化控件
     */
    protected abstract void initViews();

    /**
     * 初始化监听
     */
    protected abstract void initListeners();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        initArgs();
        initPage();
        return mRootView;
    }

    /**
     * 初始化页面, 可以重写该方法，以增强灵活性
     */
    protected void initPage() {
        initTitleBar();
        initViews();
        initListeners();
    }

    /**
     * 初始化标题，可进行重写
     */
    protected TitleBar initTitleBar() {
        return TitleUtils.addTitleBarDynamic((ViewGroup) mRootView, getPageTitle(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popToBack();
            }
        });
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (PageConfig.getInstance().isEnableWatcher()) {
            RefWatcher refWatcher = PageConfig.getInstance().getRefWatcher();
            refWatcher.watch(this);
        }
    }

    /**
     * 获取根布局
     *
     * @return
     */
    public View getRootView() {
        return mRootView;
    }

    /**
     * 获得页面名
     *
     * @return 页面名
     */
    public String getPageName() {
        return mPageName;
    }

    /**
     * 设置页面名
     *
     * @param pageName 页面名
     */
    public void setPageName(String pageName) {
        mPageName = pageName;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    protected <T extends View> T findViewById(int id) {
        return mRootView.findViewById(id);
    }

    /**
     * 页面跳转接口
     */
    public interface OnFragmentFinishListener {
        /**
         * 页面跳转返回的回调接口
         * @param requestCode 请求码
         * @param resultCode 结果码
         * @param intent
         */
        void onFragmentResult(int requestCode, int resultCode, Intent intent);
    }

}
