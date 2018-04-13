package com.xuexiang.xpage.annotation;


import com.xuexiang.xpage.enums.CoreAnim;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fragment页面信息标注
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Page {
    /**
     * 页面的名称
     * @return
     */
    String name() default "";

    /**
     * 界面传递的参数Key
     * @return
     */
    String[] params() default {""};

    /**
     * 页面切换的动画
     * @return
     */
    CoreAnim anim() default CoreAnim.slide;
}
