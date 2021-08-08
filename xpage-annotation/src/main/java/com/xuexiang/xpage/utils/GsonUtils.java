/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuexiang.xpage.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Gson工具类
 *
 * @author xuexiang
 * @since 2021/8/8 3:14 下午
 */
public final class GsonUtils {

    private static final Gson sGson = new Gson();

    private GsonUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 把单个指定类型的对象 转换为 JSON 字符串
     *
     * @param src 需要序列化的对象
     * @return JSON 字符串
     */
    public static String toJson(Object src) {
        return sGson.toJson(src);
    }

    /**
     * 把 JSON 字符串 转换为 单个指定类型的对象
     *
     * @param json     包含了单个对象数据的JSON字符串
     * @param classOfT 指定类型对象的Class
     * @return 指定类型对象
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return sGson.fromJson(json, classOfT);
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析Json字符串
     *
     * @param json    Json字符串
     * @param typeOfT 泛型类
     * @param <T>
     * @return 指定类型对象
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        try {
            return sGson.fromJson(json, typeOfT);
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
