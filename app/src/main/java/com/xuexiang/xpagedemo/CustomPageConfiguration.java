package com.xuexiang.xpagedemo;

import android.content.Context;

import com.xuexiang.xpage.PageConfiguration;
import com.xuexiang.xpage.config.AppPageConfig;
import com.xuexiang.xpage.config.ModuletestPageConfig;
import com.xuexiang.xpage.model.PageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义注册XPage的页面实现接口【已经过时，框架会自动扫描并注册】
 * <p>
 * 注意！！！这里的AppPageConfig和ModuletestPageConfig都是自动注册页面。
 * 自动注册页面,是编译时自动生成的，build一下就出来了。
 * 如果你还没使用 @Page 的话，暂时是不会生成的。
 * 另外注意注解编译器在 build.gradle 中的引入方式：
 * Java：
 * annotationProcessor 'com.github.xuexiangjys.XPage:xpage-compiler:last_version'
 * Kotlin：
 * kapt 'com.github.xuexiangjys.XPage:xpage-compiler:last_version'
 * 引入方式不对应的话，编译时也不会自动生成的。
 *
 * @author xuexiang
 * @since 2021/1/10 2:01 AM
 */
@Deprecated
public class CustomPageConfiguration implements PageConfiguration {

    @Override
    public List<PageInfo> registerPages(Context context) {
        List<PageInfo> pageInfos = new ArrayList<>();
        pageInfos.addAll(getAppPages());
        pageInfos.addAll(getModuleTestPages());
        return pageInfos;
    }

    /**
     * AppPageConfig这个是主项目app自动生成的页面信息
     */
    private List<PageInfo> getAppPages() {
        return AppPageConfig.getInstance().getPages();
    }


    /**
     * ModuletestPageConfig这个是module项目module-test自动生成的页面信息
     */
    private List<PageInfo> getModuleTestPages() {
        return ModuletestPageConfig.getInstance().getPages();
    }

}
