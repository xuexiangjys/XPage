package com.xuexiang.xpage.annotation;


import com.xuexiang.xpage.enums.CoreAnim;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fragment页面信息标注
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Page {
    /**
     * @return 页面的名称
     */
    String name() default "";

    /**
     * @return 界面传递的参数Key
     */
    String[] params() default {""};

    /**
     * @return 页面切换的动画
     */
    CoreAnim anim() default CoreAnim.slide;

    /**
     *
     * @return 拓展字段
     */
    int extra() default -1;
}
