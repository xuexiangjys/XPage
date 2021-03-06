package com.xuexiang.xpage;

import android.content.Context;


import com.xuexiang.xpage.model.PageInfo;

import java.util.List;

/**
 * 页面配置接口
 *
 * @author xuexiang
 * @since 2018/5/24 下午3:40
 */
public interface PageConfiguration {

    /**
     * 注册页面
     *
     * @param context 上下文
     * @return 注册的页面集合
     */
    List<PageInfo> registerPages(Context context);

}
