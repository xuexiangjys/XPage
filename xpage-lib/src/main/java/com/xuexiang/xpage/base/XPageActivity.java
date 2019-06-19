package com.xuexiang.xpage.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;

import com.xuexiang.xpage.PageConfig;
import com.xuexiang.xpage.R;
import com.xuexiang.xpage.core.CoreConfig;
import com.xuexiang.xpage.core.CorePageManager;
import com.xuexiang.xpage.core.CoreSwitchBean;
import com.xuexiang.xpage.core.CoreSwitcher;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.logger.PageLog;
import com.xuexiang.xpage.utils.Utils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 页面跳转都通过XPageActivity 嵌套Fragment来实现,动态替换fragment只需要指定相应的参数。 避免Activity 需要再manifest中注册的问题。
 * 1.管理应用中所有XPageActivity 实例。 2.管理XPageActivity 实例和fragment的跳转
 *
 * @author xuexiang
 * @since 2018/5/24 下午3:36
 */
public class XPageActivity extends FragmentActivity implements CoreSwitcher {
    /**
     * 应用中所有XPageActivity的引用
     */
    private static List<WeakReference<XPageActivity>> mActivities = new ArrayList<WeakReference<XPageActivity>>();
    /**
     * 记录首个CoreSwitchBean，用于页面切换
     */
    protected CoreSwitchBean mFirstCoreSwitchBean;
    /**
     * 主线程Handler
     */
    private Handler mHandler = null;
    /**
     * 当前activity的引用
     */
    private WeakReference<XPageActivity> mCurrentInstance = null;
    /**
     * ForResult 的fragment
     */
    private XPageFragment mFragmentForResult = null;
    /**
     * 请求码，必须大于等于0
     */
    private int mFragmentRequestCode = -1;

    /**
     * 仅用于接受应用退出广播，程序退出时有机会做一些必要的清理工作
     */
    private BroadcastReceiver mExitReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (CoreConfig.ACTION_EXIT_APP.equals(action)) {
                PageLog.d("exit from broadcast");
                finish();
            }
        }
    };

    /**
     * 返回最上层的activity
     *
     * @return 栈顶Activity
     */
    public static XPageActivity getTopActivity() {
        if (mActivities != null) {
            int size = mActivities.size();
            if (size >= 1) {
                WeakReference<XPageActivity> ref = mActivities.get(size - 1);
                if (ref != null) {
                    return ref.get();
                }
            }
        }
        return null;
    }

    /**
     * 广播退出时清理activity列表
     */
    public static void unInit() {
        if (mActivities != null) {
            mActivities.clear();
        }
    }

    /**
     * 获得当前活动页面名
     *
     * @return 当前页名
     */
    protected String getPageName() {
        XPageFragment frg = getActiveFragment();
        if (frg != null) {
            return frg.getPageName();
        }
        return "";
    }

    //======================页面返回退出==========================//

    /**
     * 弹出页面
     */
    @Override
    public void popPage() {
        popOrFinishActivity();

    }

    /**
     * 保证在主线程操作
     */
    private void popOrFinishActivity() {
        if (isFinishing()) {
            return;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            if (isMainThread()) {
                getSupportFragmentManager().popBackStackImmediate();
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager().popBackStackImmediate();
                    }
                });
            }
        } else {
            finishActivity(this, true);
        }

    }

    /**
     * 在同一个页面弹出fragment
     */
    @Override
    public void popPageInActivity() {
        if (isFinishing()) {
            return;
        }
        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();

        if (manager.getBackStackEntryCount() > 1) {
            if (isMainThread()) {
                manager.popBackStackImmediate();
                updateFragmentStatus(manager, transaction);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        manager.popBackStackImmediate();
                        updateFragmentStatus(manager, transaction);
                    }
                });
            }
        } else {
            finishActivity(this, true);
        }
    }

    /**
     * 解决多个fragment重叠问题。
     * 问题：当pop最上面一个fragment时，栈内其它fragment会被强制执行show方法，导致页面重叠。
     * 解决：循环遍历剩余fragment，使最后一个show，其它为hide
     * @param manager
     * @param transaction
     * @author lreal
     */
    private void updateFragmentStatus(FragmentManager manager, FragmentTransaction transaction) {
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() >= 2) {
            for (int i = 0; i < fragments.size(); i++) {
                if (i == fragments.size() - 1) {
                    transaction.show(fragments.get(i));
                } else {
                    transaction.hide(fragments.get(i));
                }
            }
            transaction.commit();
        }
    }

    /**
     * 是否是主线程
     *
     * @return 是否是主线程
     */
    private boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    /**
     * 结束activity，设置是否显示动画
     *
     * @param activity      XPageActivity对象
     * @param showAnimation 是否显示动画
     */
    private void finishActivity(XPageActivity activity, boolean showAnimation) {
        if (activity != null) {
            activity.finish();
            //从activity列表中移除当前实例
            if (getIsAddActivityToStack()) {
                mActivities.remove(mCurrentInstance);
            }

            if (showAnimation) {
                //动画
                int[] animations = null;
                if (activity.mFirstCoreSwitchBean != null && activity.mFirstCoreSwitchBean.getAnim() != null) {
                    animations = activity.mFirstCoreSwitchBean.getAnim();
                }
                if (animations != null && animations.length >= 4) {
                    overridePendingTransition(animations[2], animations[3]);
                }
            }
        }
    }


    /**
     * 是否位于栈顶
     *
     * @param fragmentTag fragment的tag
     * @return 指定Fragment是否位于栈顶
     */
    @Override
    public boolean isFragmentTop(String fragmentTag) {
        int size = mActivities.size();
        if (size > 0) {
            WeakReference<XPageActivity> ref = mActivities.get(size - 1);
            XPageActivity item = ref.get();
            if (item != null && item == this) {
                FragmentActivity activity = item;
                FragmentManager manager = activity.getSupportFragmentManager();
                if (manager != null) {
                    int count = manager.getBackStackEntryCount();
                    if (count >= 1) {
                        FragmentManager.BackStackEntry entry = manager.getBackStackEntryAt(count - 1);
                        return entry.getName().equalsIgnoreCase(fragmentTag);
                    }
                }
            }
        }
        return false;
    }

    /**
     * 查找fragment
     *
     * @param pageName page的名字
     * @return 是否找到对应Fragment
     */
    @Override
    public boolean findPage(String pageName) {
        int size = mActivities.size();
        boolean hasFind = false;
        for (int j = size - 1; j >= 0; j--) {
            WeakReference<XPageActivity> ref = mActivities.get(j);
            if (ref != null) {
                XPageActivity item = ref.get();
                if (item == null) {
                    PageLog.d("item is null");
                    continue;
                }
                FragmentManager manager = item.getSupportFragmentManager();
                int count = manager.getBackStackEntryCount();
                for (int i = count - 1; i >= 0; i--) {
                    String name = manager.getBackStackEntryAt(i).getName();
                    if (name.equalsIgnoreCase(pageName)) {
                        hasFind = true;
                        break;
                    }
                }
                if (hasFind) {
                    break;
                }
            }
        }
        return hasFind;
    }

    /**
     * 弹出并用bundle刷新数据，在onFragmentDataReset中回调
     *
     * @param page page的名字
     * @return 跳转到对应的fragment的对象
     */
    @Override
    public Fragment gotoPage(CoreSwitchBean page) {
        if (page == null) {
            PageLog.e("page name empty");
            return null;
        }
        String pageName = page.getPageName();
        if (!findPage(pageName)) {
            PageLog.d("Be sure you have the right pageName" + pageName);
            return this.openPage(page);
        }

        int size = mActivities.size();
        for (int i = size - 1; i >= 0; i--) {
            WeakReference<XPageActivity> ref = mActivities.get(i);
            if (ref != null) {
                XPageActivity item = ref.get();
                if (item == null) {
                    PageLog.d("item null");
                    continue;
                }

                boolean findInActivity = popFragmentInActivity(pageName, page.getBundle(), item);
                if (findInActivity) {
                    break;
                } else {
                    // 找不到就弹出
                    item.finish();
                }
            }
        }
        return null;
    }

    /**
     * 当前activity中弹fragment
     *
     * @param pageName     page的名字
     * @param bundle       传递的参数
     * @param findActivity 当前activity
     * @return 是否弹出成功
     */
    protected boolean popFragmentInActivity(final String pageName, Bundle bundle, XPageActivity findActivity) {
        if (pageName == null || findActivity == null || findActivity.isFinishing()) {
            return false;
        } else {
            final FragmentManager fragmentManager = findActivity.getSupportFragmentManager();
            if (fragmentManager != null) {
                Fragment frg = fragmentManager.findFragmentByTag(pageName);
                if (frg != null && frg instanceof XPageFragment) {
                    if (fragmentManager.getBackStackEntryCount() > 1 && mHandler != null) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fragmentManager.popBackStack(pageName, 0);
                            }
                        }, 100);
                    }
                    ((XPageFragment) frg).onFragmentDataReset(bundle);
                    return true;
                }
            }
        }
        return false;
    }

    //==================startActivity=======================//

    /**
     * 根据SwitchPage打开activity
     *
     * @param page CoreSwitchBean对象
     */
    public void startActivity(CoreSwitchBean page) {
        try {
            Intent intent = new Intent(this, page.getContainActivityClazz());
            intent.putExtra(CoreSwitchBean.KEY_SWITCH_BEAN, page);

            this.startActivity(intent);
            int[] animations = page.getAnim();
            if (animations != null && animations.length >= 2) {
                overridePendingTransition(animations[0], animations[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            PageLog.e(e);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if (intent == null) {
            PageLog.e("[startActivity failed]: intent == null");
            return;
        }
        if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            try {
                super.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                PageLog.e(e);
            }
        } else {
            PageLog.e("[resolveActivity failed]: " + intent.getComponent().getClassName() + " do not register in manifest");
        }
    }

    /**
     * 给BaseFragment调用
     *
     * @param page     CoreSwitchBean对象
     * @param fragment 要求返回结果的BaseFragment对象
     * @return 打开的fragment对象
     */
    @Override
    public Fragment openPageForResult(CoreSwitchBean page, XPageFragment fragment) {
        if (page != null) {
            if (page.isNewActivity()) {
                PageLog.d("openPageForResult start new activity-----" + fragment.getPageName());
                mFragmentForResult = fragment;
                mFragmentRequestCode = page.getRequestCode();
                startActivityForResult(page);
                return null;
            } else {
                String pageName = page.getPageName();
                Bundle bundle = page.getBundle();
                int[] animations = page.getAnim();
                boolean addToBackStack = page.isAddToBackStack();
                XPageFragment frg = CorePageManager.getInstance().openPageWithNewFragmentManager(getSupportFragmentManager(), pageName, bundle, animations, addToBackStack);
                if (frg == null) {
                    return null;
                }
                final XPageFragment opener = fragment;
                frg.setRequestCode(page.getRequestCode());
                frg.setFragmentFinishListener(new XPageFragment.OnFragmentFinishListener() {
                    @Override
                    public void onFragmentResult(int requestCode, int resultCode, Intent intent) {
                        opener.onFragmentResult(requestCode, resultCode, intent);
                    }
                });
                return frg;
            }
        } else {
            PageLog.d("openPageForResult.SwitchBean is null");
        }
        return null;
    }

    /**
     * @param page CoreSwitchBean对象
     */
    public void startActivityForResult(CoreSwitchBean page) {
        try {
            Intent intent = new Intent(this, page.getContainActivityClazz());
            intent.putExtra(CoreSwitchBean.KEY_SWITCH_BEAN, page);
            intent.putExtra(CoreSwitchBean.KEY_START_ACTIVITY_FOR_RESULT, true);
            this.startActivityForResult(intent, page.getRequestCode());

            int[] animations = page.getAnim();
            if (animations != null && animations.length >= 2) {
                this.overridePendingTransition(animations[0], animations[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (intent == null) {
            PageLog.e("[startActivity failed]: intent == null");
            return;
        }
        if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            try {
                super.startActivityForResult(intent, requestCode);
            } catch (Exception e) {
                e.printStackTrace();
                PageLog.e(e);
            }
        } else {
            PageLog.e("[resolveActivity failed]: " + intent.getComponent().getClassName() + " do not register in manifest");
        }
    }

    //==================openPage 打开页面=======================//

    /**
     * 根据SwitchBean打开一个新的fragment
     *
     * @param page CoreSwitchBean对象
     * @return 打开的Fragment对象
     */
    @Override
    public Fragment openPage(CoreSwitchBean page) {
        boolean addToBackStack = page.isAddToBackStack();
        boolean newActivity = page.isNewActivity();
        Bundle bundle = page.getBundle();

        int[] animations = page.getAnim();
        if (newActivity) {
            startActivity(page);
            return null;
        } else {
            String pageName = page.getPageName();
            return CorePageManager.getInstance().openPageWithNewFragmentManager(getSupportFragmentManager(), pageName, bundle, animations, addToBackStack);
        }
    }

    /**
     * 根据SwitchBean切换fragment
     *
     * @param page CoreSwitchBean对象
     * @return 打开的Fragment对象
     */
    @Override
    public Fragment changePage(CoreSwitchBean page) {
        boolean addToBackStack = page.isAddToBackStack();
        boolean newActivity = page.isNewActivity();
        Bundle bundle = page.getBundle();

        int[] animations = page.getAnim();
        if (newActivity) {
            startActivity(page);
            return null;
        } else {
            String pageName = page.getPageName();
            return CorePageManager.getInstance().changePageWithNewFragmentManager(getSupportFragmentManager(), pageName, bundle, animations, addToBackStack);
        }
    }

    /**
     * 移除无用fragment
     *
     * @param fragmentLists 移除的fragment列表
     */
    @Override
    public void removeUnlessFragment(List<String> fragmentLists) {
        if (isFinishing()) {
            return;
        }
        FragmentManager manager = getSupportFragmentManager();
        if (manager != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            for (String tag : fragmentLists) {
                Fragment fragment = manager.findFragmentByTag(tag);
                if (fragment != null) {
                    transaction.remove(fragment);
                }
            }
            transaction.commitAllowingStateLoss();
            int count = manager.getBackStackEntryCount();
            if (count == 0) {
                finish();
            }
        }
    }

    //========================= 简单地打开fragment ==============================//

    /**
     * 打开fragment
     *
     * @param pageName 页面名
     * @return 打开的fragment对象
     */
    public Fragment openPage(String pageName) {
        CoreSwitchBean page = new CoreSwitchBean(pageName, null, CoreAnim.slide);
        return openPage(page);
    }

    /**
     * 打开fragment
     *
     * @param pageName 页面名
     * @return 打开的fragment对象
     */
    public Fragment openPage(String pageName, Bundle bundle) {
        CoreSwitchBean page = new CoreSwitchBean(pageName, bundle, CoreAnim.slide);
        return openPage(page);
    }

    /**
     * 打开fragment
     *
     * @param pageName 页面名
     * @param bundle   参数
     * @param coreAnim 动画
     * @return 打开的fragment对象
     */
    public Fragment openPage(String pageName, Bundle bundle, CoreAnim coreAnim) {
        CoreSwitchBean page = new CoreSwitchBean(pageName, bundle, coreAnim);
        return openPage(page);
    }

    /**
     * 打开fragment
     *
     * @return 打开的fragment对象
     */
    public <T extends XPageFragment> T openPage(Class<T> clazz) {
        CoreSwitchBean page = new CoreSwitchBean(clazz);
        return (T) openPage(page);
    }

    /**
     * 打开fragment
     *
     * @param clazz  页面类
     * @param bundle 参数
     * @return 打开的fragment对象
     */
    public <T extends XPageFragment> T openPage(Class<T> clazz, Bundle bundle) {
        CoreSwitchBean page = new CoreSwitchBean(clazz, bundle);
        return (T) openPage(page);
    }

    //==================== 打开fragment的高级操作 ============================//

    /**
     * 打开fragment，并设置是否新开activity，设置是否添加到返回栈
     *
     * @param pageName       页面名
     * @param bundle         参数
     * @param coreAnim       动画
     * @param addToBackStack 返回栈
     * @param newActivity    新activity
     * @return 打开的fragment对象
     */
    public Fragment openPage(String pageName, Bundle bundle, CoreAnim coreAnim, boolean addToBackStack, boolean newActivity) {
        CoreSwitchBean page = new CoreSwitchBean(pageName, bundle, coreAnim, addToBackStack, newActivity);
        return openPage(page);
    }

    /**
     * 打开fragment，并设置是否新开activity，设置是否添加到返回栈
     *
     * @param pageName       页面名
     * @param bundle         参数
     * @param anim           动画
     * @param addToBackStack 返回栈
     * @param newActivity    新activity
     * @return 打开的fragment对象
     */
    public Fragment openPage(String pageName, Bundle bundle, int[] anim, boolean addToBackStack, boolean newActivity) {
        CoreSwitchBean page = new CoreSwitchBean(pageName, bundle, anim, addToBackStack, newActivity);
        return openPage(page);
    }

    /**
     * 打开fragment，并设置是否添加到返回栈
     *
     * @param pageName       页面名
     * @param bundle         参数
     * @param coreAnim       动画
     * @param addToBackStack 返回栈
     * @return 打开的fragment对象
     */
    public Fragment openPage(String pageName, Bundle bundle, CoreAnim coreAnim, boolean addToBackStack) {
        CoreSwitchBean page = new CoreSwitchBean(pageName, bundle, coreAnim, addToBackStack);
        return openPage(page);
    }

    /**
     * 打开fragment，并设置是否添加到返回栈
     *
     * @param pageName       页面名
     * @param bundle         参数
     * @param anim           动画
     * @param addToBackStack 返回栈
     * @return 打开的fragment对象
     */
    public Fragment openPage(String pageName, Bundle bundle, int[] anim, boolean addToBackStack) {
        CoreSwitchBean page = new CoreSwitchBean(pageName, bundle, anim, addToBackStack);
        return openPage(page);
    }


    /**
     * 打开fragment
     *
     * @param pageName 页面名
     * @param bundle   参数
     * @param anim     动画
     * @return 打开的fragment对象
     */
    public Fragment openPage(String pageName, Bundle bundle, int[] anim) {
        CoreSwitchBean page = new CoreSwitchBean(pageName, bundle, anim);
        return openPage(page);
    }

    //========================= 切换fragment[直接替换，不增加到返回堆栈]==============================//

    /**
     * 切换fragment[直接替换，不增加到返回堆栈]
     *
     * @return 打开的fragment对象
     */
    public <T extends XPageFragment> T changePage(Class<T> clazz) {
        CoreSwitchBean page = new CoreSwitchBean(PageConfig.getPageInfo(clazz).getName(), null, PageConfig.getPageInfo(clazz).getAnim()).setAddToBackStack(false);
        return (T) changePage(page);
    }

    /**
     * 切换fragment[直接替换，不增加到返回堆栈]
     *
     * @param clazz  页面类
     * @param bundle 参数
     * @return 打开的fragment对象
     */
    public <T extends XPageFragment> T changePage(Class<T> clazz, Bundle bundle) {
        CoreSwitchBean page = new CoreSwitchBean(PageConfig.getPageInfo(clazz).getName(), bundle, PageConfig.getPageInfo(clazz).getAnim()).setAddToBackStack(false);
        return (T) changePage(page);
    }

    /**
     * 切换fragment[直接替换，增加到返回堆栈]
     *
     * @param clazz  页面类
     * @param bundle 参数
     * @return 打开的fragment对象
     */
    public <T extends XPageFragment> T changePage(Class<T> clazz, Bundle bundle, boolean addToBackStack) {
        CoreSwitchBean page = new CoreSwitchBean(PageConfig.getPageInfo(clazz).getName(), bundle, PageConfig.getPageInfo(clazz).getAnim()).setAddToBackStack(addToBackStack);
        return (T) changePage(page);
    }

    /**
     * 切换fragment[直接替换，不增加到返回堆栈]
     *
     * @param pageName 页面名
     * @return 切换的fragment对象
     */
    public Fragment changePage(String pageName) {
        CoreSwitchBean page = new CoreSwitchBean(pageName, null, CoreAnim.none).setAddToBackStack(false);
        return changePage(page);
    }

    /**
     * 切换fragment[直接替换，不增加到返回堆栈]
     *
     * @param pageName 页面名
     * @param bundle   参数
     * @return 切换的fragment对象
     */
    public Fragment changePage(String pageName, Bundle bundle) {
        CoreSwitchBean page = new CoreSwitchBean(pageName, bundle, CoreAnim.none).setAddToBackStack(false);
        return changePage(page);
    }

    /**
     * 切换fragment[直接替换，增加到返回堆栈]
     *
     * @param pageName 页面名
     * @param bundle   参数
     * @return 切换的fragment对象
     */
    public Fragment changePage(String pageName, Bundle bundle, boolean addToBackStack) {
        CoreSwitchBean page = new CoreSwitchBean(pageName, bundle, CoreAnim.none).setAddToBackStack(addToBackStack);
        return changePage(page);
    }

    /**
     * 切换fragment[直接替换，不增加到返回堆栈]
     *
     * @param pageName 页面名
     * @param bundle   参数
     * @param coreAnim 动画
     * @return 切换的fragment对象
     */
    public Fragment changePage(String pageName, Bundle bundle, CoreAnim coreAnim) {
        CoreSwitchBean page = new CoreSwitchBean(pageName, bundle, coreAnim).setAddToBackStack(false);
        return changePage(page);
    }

    //==================生命周期=======================//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

        //处理新开activity的情况
        Intent newIntent = getIntent();
        if (savedInstanceState != null) {
            //恢复数据，需要用注解SaveWithActivity
            loadActivitySavedData(savedInstanceState);
        }
        //获得主线程handler
        mHandler = new Handler(getMainLooper());

        if (getIsAddActivityToStack()) {
            //当前activity弱引用
            mCurrentInstance = new WeakReference<>(this);
            //当前activity增加到activity列表中
            mActivities.add(mCurrentInstance);
            //打印所有activity情况
            printAllActivities();
        }

        //处理新开activity跳转
        init(newIntent);

        //注册本地广播，接收程序退出广播
        if (getIsRegisterExitBroadcast()) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(CoreConfig.ACTION_EXIT_APP);
            filter.addCategory(Intent.CATEGORY_DEFAULT);
            CoreConfig.getLocalBroadcastManager().registerReceiver(mExitReceiver, filter);
        }
    }

    /**
     * 获取是否将activity添加到堆栈中
     *
     * @return {@code true} :添加<br> {@code false} : 不添加
     */
    protected boolean getIsAddActivityToStack() {
        return true;
    }

    /**
     * 获取是否注册页面结束的广播
     *
     * @return {@code true} :注册<br> {@code false} : 不注册
     */
    protected boolean getIsRegisterExitBroadcast() {
        return true;
    }


    /**
     * 设置根布局
     */
    protected void setContentView() {
        int layoutId = getLayoutId();
        if (layoutId != -1) {
            setContentView(layoutId);
        } else {
            setContentView(getBaseLayout());
        }
    }

    /**
     * @return 获取布局的id
     */
    protected int getLayoutId() {
        return -1;
    }

    /**
     * 设置根布局
     *
     * @return
     */
    protected View getBaseLayout() {
        FrameLayout baseLayout = new FrameLayout(this);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        baseLayout.setId(R.id.fragment_container);
        baseLayout.setLayoutParams(params);
        return baseLayout;
    }

    @Override
    protected void onDestroy() {
        //解决内存泄露
        if (getIsRegisterExitBroadcast()) {
            CoreConfig.getLocalBroadcastManager().unregisterReceiver(mExitReceiver);
        }
        super.onDestroy();
    }

    /**
     * 如果fragment中处理了则fragment处理否则activity处理
     *
     * @param keyCode keyCode码
     * @param event   KeyEvent对象
     * @return 是否处理时间
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        XPageFragment activeFragment = getActiveFragment();
        boolean isHandled = false;
        if (activeFragment != null) {
            isHandled = activeFragment.onKeyDown(keyCode, event);
        }
        if (!isHandled) {
            return super.onKeyDown(keyCode, event);
        } else {
            return isHandled;
        }
    }

    /**
     * 如果是fragment发起的由fragment处理，否则默认处理
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        PageLog.d("onActivityResult from XPageActivity" + requestCode + " " + resultCode);
        if (mFragmentRequestCode == requestCode && mFragmentForResult != null) {
            mFragmentForResult.onFragmentResult(mFragmentRequestCode, resultCode, data);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 如果当前activity中只有一个activity，则关闭activity，否则父类处理
     */
    @Override
    public void onBackPressed() {
        if (this.getSupportFragmentManager().getBackStackEntryCount() == 1) {
            this.finishActivity(this, true);

        } else {
            super.onBackPressed();
        }
    }

    /**
     * 获得当前活动fragment
     *
     * @return 当前活动Fragment对象
     */
    public XPageFragment getActiveFragment() {
        if (this.isFinishing()) {
            return null;
        }
        FragmentManager manager = this.getSupportFragmentManager();
        if (manager != null) {
            int count = manager.getBackStackEntryCount();
            if (count > 0) {
                String tag = manager.getBackStackEntryAt(count - 1).getName();
                return (XPageFragment) manager.findFragmentByTag(tag);
            }
        }
        return null;
    }

    /**
     * 根据页面的类获取XPageFragment
     *
     * @return 获取的页面
     */
    public <T extends XPageFragment> T getPage(Class<T> clazz) {
        String pageName = PageConfig.getPageInfo(clazz).getName();
        XPageFragment fragment = getPageByName(pageName);
        if (fragment != null) {
            return (T) fragment;
        } else {
            return null;
        }
    }

    /**
     * 根据页面的名称获取XPageFragment
     *
     * @return 获取的页面
     */
    public XPageFragment getPageByName(String pageName) {
        if (this.isFinishing() || TextUtils.isEmpty(pageName)) {
            return null;
        }
        FragmentManager manager = this.getSupportFragmentManager();
        if (manager != null) {
            return (XPageFragment) manager.findFragmentByTag(pageName);
        }
        return null;
    }

    /**
     * 打印，调试用
     */
    protected void printAllActivities() {
        PageLog.d("------------XPageActivity print all------------activities size:" + mActivities.size());
        for (WeakReference<XPageActivity> ref : mActivities) {
            if (ref != null) {
                XPageActivity item = ref.get();
                if (item != null) {
                    PageLog.d(item.toString());
                }
            }
        }
    }

    /**
     * 初始化intent
     *
     * @param newIntent Intent对象
     */
    private void init(Intent newIntent) {
        try {
            CoreSwitchBean page = newIntent.getParcelableExtra(CoreSwitchBean.KEY_SWITCH_BEAN);
            boolean startActivityForResult = newIntent.getBooleanExtra(CoreSwitchBean.KEY_START_ACTIVITY_FOR_RESULT, false);
            mFirstCoreSwitchBean = page;
            if (page != null) {
                XPageFragment fragment = null;
                boolean addToBackStack = page.isAddToBackStack();
                String pageName = page.getPageName();
                Bundle bundle = page.getBundle();
                fragment = CorePageManager.getInstance().openPageWithNewFragmentManager(getSupportFragmentManager(), pageName, bundle, null, addToBackStack);
                if (fragment != null) {
                    if (startActivityForResult) {
                        fragment.setRequestCode(page.getRequestCode());
                        fragment.setFragmentFinishListener(new XPageFragment.OnFragmentFinishListener() {
                            @Override
                            public void onFragmentResult(int requestCode, int resultCode, Intent intent) {
                                XPageActivity.this.setResult(resultCode, intent);
                            }
                        });
                    }
                } else {
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PageLog.e(e);
            finish();
        }
    }

    @Override
    protected void onStop() {
        if (isFinishing()) {
            onRelease();
        }
        super.onStop();
    }

    /**
     * 资源释放
     */
    protected void onRelease() {

    }

    //==============数据保存=================//

    /**
     * 保存数据
     *
     * @param outState Bundle对象
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Field[] fields = this.getClass().getDeclaredFields();
        Field.setAccessible(fields, true);
        Annotation[] ans;
        for (Field f : fields) {
            ans = f.getDeclaredAnnotations();
            for (Annotation an : ans) {
                if (an instanceof SaveWithActivity) {
                    try {
                        Object o = f.get(this);
                        if (o == null) {
                            continue;
                        }
                        String fieldName = f.getName();
                        if (o instanceof Integer) {
                            outState.putInt(fieldName, f.getInt(this));
                        } else if (o instanceof String) {
                            outState.putString(fieldName, (String) f.get(this));
                        } else if (o instanceof Long) {
                            outState.putLong(fieldName, f.getLong(this));
                        } else if (o instanceof Short) {
                            outState.putShort(fieldName, f.getShort(this));
                        } else if (o instanceof Boolean) {
                            outState.putBoolean(fieldName, f.getBoolean(this));
                        } else if (o instanceof Byte) {
                            outState.putByte(fieldName, f.getByte(this));
                        } else if (o instanceof Character) {
                            outState.putChar(fieldName, f.getChar(this));
                        } else if (o instanceof CharSequence) {
                            outState.putCharSequence(fieldName, (CharSequence) f.get(this));
                        } else if (o instanceof Float) {
                            outState.putFloat(fieldName, f.getFloat(this));
                        } else if (o instanceof Double) {
                            outState.putDouble(fieldName, f.getDouble(this));
                        } else if (o instanceof String[]) {
                            outState.putStringArray(fieldName, (String[]) f.get(this));
                        } else if (o instanceof Parcelable) {
                            outState.putParcelable(fieldName, (Parcelable) f.get(this));
                        } else if (o instanceof Serializable) {
                            outState.putSerializable(fieldName, (Serializable) f.get(this));
                        } else if (o instanceof Bundle) {
                            outState.putBundle(fieldName, (Bundle) f.get(this));
                        }
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                        PageLog.e(e);
                    }
                }
            }
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * 恢复数据
     *
     * @param savedInstanceState Bundle对象
     */
    private void loadActivitySavedData(Bundle savedInstanceState) {
        Field[] fields = this.getClass().getDeclaredFields();
        Field.setAccessible(fields, true);
        Annotation[] ans;
        for (Field f : fields) {
            ans = f.getDeclaredAnnotations();
            for (Annotation an : ans) {
                if (an instanceof SaveWithActivity) {
                    try {
                        String fieldName = f.getName();
                        @SuppressWarnings("rawtypes")
                        Class cls = f.getType();
                        if (cls == int.class || cls == Integer.class) {
                            f.setInt(this, savedInstanceState.getInt(fieldName));
                        } else if (String.class.isAssignableFrom(cls)) {
                            f.set(this, savedInstanceState.getString(fieldName));
                        } else if (Serializable.class.isAssignableFrom(cls)) {
                            f.set(this, savedInstanceState.getSerializable(fieldName));
                        } else if (cls == long.class || cls == Long.class) {
                            f.setLong(this, savedInstanceState.getLong(fieldName));
                        } else if (cls == short.class || cls == Short.class) {
                            f.setShort(this, savedInstanceState.getShort(fieldName));
                        } else if (cls == boolean.class || cls == Boolean.class) {
                            f.setBoolean(this, savedInstanceState.getBoolean(fieldName));
                        } else if (cls == byte.class || cls == Byte.class) {
                            f.setByte(this, savedInstanceState.getByte(fieldName));
                        } else if (cls == char.class || cls == Character.class) {
                            f.setChar(this, savedInstanceState.getChar(fieldName));
                        } else if (CharSequence.class.isAssignableFrom(cls)) {
                            f.set(this, savedInstanceState.getCharSequence(fieldName));
                        } else if (cls == float.class || cls == Float.class) {
                            f.setFloat(this, savedInstanceState.getFloat(fieldName));
                        } else if (cls == double.class || cls == Double.class) {
                            f.setDouble(this, savedInstanceState.getDouble(fieldName));
                        } else if (String[].class.isAssignableFrom(cls)) {
                            f.set(this, savedInstanceState.getStringArray(fieldName));
                        } else if (Parcelable.class.isAssignableFrom(cls)) {
                            f.set(this, savedInstanceState.getParcelable(fieldName));
                        } else if (Bundle.class.isAssignableFrom(cls)) {
                            f.set(this, savedInstanceState.getBundle(fieldName));
                        }
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                        PageLog.e(e);
                    }
                }
            }
        }
    }

    /**
     * 注解了该注解数据会被保存
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface SaveWithActivity {

    }

    /**
     * -------------------------------------点击非输入区域键盘消失--------------------------------------------
     **/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (Utils.isShouldHideInput(v, ev)) {
                Utils.hideSoftInput(v);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
