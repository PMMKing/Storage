package com.yuan.storage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IStorage {
    boolean putSerializable(String key, Serializable value);

    boolean putBytes(String key, byte[] value);

    boolean putInt(String key, int value);

    boolean putShort(String key, short value);

    boolean putLong(String key, long value);

    boolean putFloat(String key, float value);

    boolean putDouble(String key, double value);

    boolean putString(String key, String value);

    boolean putBoolean(String key, boolean value);

    <T extends Serializable> T getSerializable(String key, Class<T> clazz, T value);

    int getInt(String key, int defaul);

    double getDouble(String key, double defaul);

    float getFloat(String key, float defaul);

    short getShort(String key, short defaul);

    long getLong(String key, long defaul);

    String getString(String key, String defaul);

    boolean getBoolean(String key, boolean defaul);

    byte[] getBytes(String key, byte[] defaul);

    boolean remove(String key);

    boolean contains(String key);

}
