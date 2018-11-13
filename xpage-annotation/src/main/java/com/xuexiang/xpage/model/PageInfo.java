package com.xuexiang.xpage.model;

import com.alibaba.fastjson.JSON;
import com.xuexiang.xpage.enums.CoreAnim;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 页面信息
 *
 * @author xuexiang
 */
public class PageInfo implements Serializable {

    /**
     * 页面名
     */
    private String name;
    /**
     * 页面class
     */
    private String classPath;
    /**
     * 页面传递的参数
     */
    private String params = "";

    /**
     * 页面跳转的动画
     */
    private CoreAnim anim;

    /**
     * 拓展字段
     */
    private int extra;

    public PageInfo() {

    }

    public PageInfo(Class<?> clazz) {
        this(clazz.getSimpleName(), clazz.getName());
    }

    public PageInfo(String name, String classPath) {
        this.name = name;
        this.classPath = classPath;
    }

    public PageInfo(String name, Class<?> clazz) {
        this.name = name;
        this.classPath = clazz.getName();
    }

    public PageInfo(String name, String classPath, String params) {
        this.name = name;
        this.classPath = classPath;
        this.params = params;
    }

    public PageInfo(String name, Class<?> clazz, Map<String, Object> params) {
        this.name = name;
        this.classPath = clazz.getName();
        this.params = JSON.toJSONString(params);
    }

    /**
     * 自动编译生成需要使用的构造函数
     *
     * @param name
     * @param classPath
     * @param params
     * @param anim
     */
    public PageInfo(String name, String classPath, String params, CoreAnim anim, int extra) {
        this.name = name;
        this.classPath = classPath;
        this.params = params;
        this.anim = anim;
        this.extra = extra;
    }

    public static String getParams(String[] params) {
        if (params != null && params.length > 0) {
            Map<String, Object> paramMaps = new HashMap<>();
            for (int i = 0; i < params.length; i++) {
                paramMaps.put(params[i], "");
            }
            return JSON.toJSONString(paramMaps);
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public PageInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getClassPath() {
        return classPath;
    }

    public PageInfo setClassPath(String classPath) {
        this.classPath = classPath;
        return this;
    }

    public PageInfo setClassPath(Class<?> clazz) {
        classPath = clazz.getName();
        return this;
    }

    public String getParams() {
        return params;
    }

    public PageInfo setParams(String params) {
        this.params = params;
        return this;
    }

    public PageInfo setParams(Map<String, Object> params) {
        this.params = JSON.toJSONString(params);
        return this;
    }

    public PageInfo setParams(String[] params) {
        if (params != null && params.length > 0) {
            Map<String, Object> paramMaps = new HashMap<>();
            for (int i = 0; i < params.length; i++) {
                paramMaps.put(params[i], "");
            }
            setParams(paramMaps);
        }
        return this;
    }

    public CoreAnim getAnim() {
        return anim;
    }

    public PageInfo setAnim(CoreAnim anim) {
        this.anim = anim;
        return this;
    }

    public int getExtra() {
        return extra;
    }

    public PageInfo setExtra(int extra) {
        this.extra = extra;
        return this;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "name='" + name + '\'' +
                ", classPath='" + classPath + '\'' +
                ", params='" + params + '\'' +
                ", anim=" + anim +
                ", extra=" + extra +
                '}';
    }
}
