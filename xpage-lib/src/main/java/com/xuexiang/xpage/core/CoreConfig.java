package com.xuexiang.xpage.core;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.alibaba.fastjson.JSON;
import com.xuexiang.xpage.base.BaseActivity;
import com.xuexiang.xpage.model.PageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局配置类
 * @author xuexiang
 * @date 2018/1/6 下午10:00
 */
public class CoreConfig {
	/**
	 * Atlas支持 start
	 */
	private static boolean isOpenAtlas = false;
	private static ClassLoader mBundleClassLoader = null;

	public static boolean isOpenAtlas() {
		return isOpenAtlas;
	}

	public static void setIsOpenAtlas(boolean isOpenAtlasFlag) {
		isOpenAtlas = isOpenAtlasFlag;
	}

	public static ClassLoader getBundleClassLoader() {
		return mBundleClassLoader;
	}

	public static void setBundleClassLoader(ClassLoader classLoader) {
		mBundleClassLoader = classLoader;
	}

	/**
	 * Atlas支持 end
	 */
	public final static String ACTION_EXIT_APP = "com.xuexiang.view.corepage.core.exit";
	// 本地广播退出

	private static LocalBroadcastManager mLocalBroadcatManager;
	private static Context mContext;

	/**
	 * 默认初始化，配置文件在assets/corepage.json
	 * 
	 * @param context
	 *            上下文
	 */
	public static void init(Context context) {
		mContext = context.getApplicationContext();
		CorePageManager.getInstance().init(mContext);
	}

	/**
	 * 自定义初始化，配置文件信息由外部传入。
	 * 
	 * @param context
	 *            上下文
	 * @param pageJson
	 *            配置的json
	 */
	public static void init(Context context, String pageJson) {
		mContext = context.getApplicationContext();
		CorePageManager.getInstance().init(mContext, pageJson);
	}
	
	/**
	 * 自定义初始化，配置文件信息由外部传入。
	 * 
	 * @param context
	 *            上下文
	 * @param pageInfoList
	 *            配置的页面信息
	 */
	public static void init(Context context, List<PageInfo> pageInfoList) {
		CoreConfig.init(context, JSON.toJSONString(pageInfoList));
	}
	
	/**
	 * 自定义初始化，配置文件信息由外部传入。
	 * 
	 * @param context
	 *            上下文
	 * @param pageInfo
	 *            配置的页面信息
	 */
	public static void init(Context context, PageInfo pageInfo) {
		List<PageInfo> list = new ArrayList<PageInfo>();
		list.add(pageInfo);
		CoreConfig.init(context, list);
	}

	public static void unInit() {
		Intent intent = new Intent();
		intent.setAction(CoreConfig.ACTION_EXIT_APP);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		getLocalBroadcastManager().sendBroadcast(intent);
		BaseActivity.unInit();
		mLocalBroadcatManager = null;
	}

	public static void readConfig(String pageJson) {
		CorePageManager.getInstance().readConfig(pageJson);
	}

	/**
	 * 发送本地广播退出程序
	 */
	public void exitApp() {
		Intent intent = new Intent();
		intent.setAction(CoreConfig.ACTION_EXIT_APP);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		getLocalBroadcastManager().sendBroadcast(intent);
		BaseActivity.unInit();
	}

	/**
	 * 获得LocalBroadcastManager对象
	 * 
	 * @return LocalBroadcastManager对象
	 */
	public static LocalBroadcastManager getLocalBroadcastManager() {
		if (mLocalBroadcatManager == null) {
			mLocalBroadcatManager = LocalBroadcastManager.getInstance(mContext);
		}
		return mLocalBroadcatManager;
	}
}
