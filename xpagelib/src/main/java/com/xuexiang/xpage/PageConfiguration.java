package com.xuexiang.xpage;

import android.content.Context;


import com.xuexiang.xpage.model.PageInfo;

import java.util.List;

/**
 * 页面配置接口
 * @author xuexiang
 * @date 2018/1/7 下午6:08
 */
public interface PageConfiguration {

    /**
     * 注册页面
     * @param context
     */
    List<PageInfo> registerPages(Context context);

}
