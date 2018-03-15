package com.yuan.library;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.Serializable;

/**
 * Created by shucheng.qu on 2018/3/14
 */

public class Store {

    private static Storage storage;
    private Context mContext;

    public static void init(Context context, String fileName) {
        storage = Storage.newStorage(context, fileName);
    }

    public static void refreshStore(Context context, String fileName) {
        storage = Storage.newStorage(context, "fileName");
    }

    public static void remove(String key) {
        if (key == null) {
            return;
        }
        storage.remove(key);
    }

    public static void put(String key, String value) {
        storage.putString(key, value);
    }

    public static void put(String key, boolean value) {
        storage.putBoolean(key, value);
    }

    public static void put(String key, int value) {
        storage.putInt(key, value);
    }

    public static void put(String key, long value) {
        storage.putLong(key, value);
    }

    public static void put(String key, float value) {
        storage.putFloat(key, value);
    }

    public static void put(String key, Serializable value) {
        storage.putSerializable(key, value);
    }

//    /**
//     * 存泛型(List/Map 等...)
//     */
//    public static void putGenerics(String key, Serializable value) {
//        storage.putString(key, JSON.toJSONString(value));
//    }

    public static boolean get(String key, boolean defaultValue) {
        try {
            return storage.getBoolean(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int get(String key, int defValue) {
        try {
            return storage.getInt(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static String get(String key, String defValue) {
        try {
            return storage.getString(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static long get(String key, long defValue) {
        try {
            return storage.getLong(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static float get(String key, float defValue) {
        try {
            return storage.getFloat(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static <T extends Serializable> T get(String key, Class<T> clazz, T defValue) {
        try {
            return storage.getSerializable(key, clazz, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }
//
//    /**
//     * 取泛型(List/Map 等...)用这个
//     */
//    public static <T extends Serializable> T getGenerics(String key, TypeReference<T> type) {
//        try {
//            return JSON.parseObject(storage.getString(key, null), type);
//        } catch (Exception e) {
//            return null;
//        }
//    }

}
