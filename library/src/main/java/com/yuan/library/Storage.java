package com.yuan.library;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.Serializable;

/**
 * Created by shucheng.qu on 2018/3/14
 */

public class Storage {
    private IStorage mProxy;

    public static Storage newStorage(Context context,String fileName) {
        return new Storage(context,fileName);
    }

    private Storage(Context context,String fileName) {
        File file1 = new File(context.getFilesDir().getAbsolutePath(), "storage");
        this.mProxy = FileStorage.newInstance(context, new File(file1, fileName));
    }

    public boolean putString(String key, String value) {
        return this.mProxy.putString(key, value);
    }

    public boolean putBoolean(String key, boolean value) {
        return this.mProxy.putBoolean(key, value);
    }

    public boolean putBytes(String key, byte[] bs) {
        return this.mProxy.putBytes(key, bs);
    }

    public boolean putInt(String key, int i) {
        return this.mProxy.putInt(key, i);
    }

    public boolean putShort(String key, short i) {
        return this.mProxy.putShort(key, i);
    }

    public boolean putLong(String key, long value) {
        return this.mProxy.putLong(key, value);
    }

    public boolean putFloat(String key, float value) {
        return this.mProxy.putFloat(key, value);
    }

    public boolean putDouble(String key, double value) {
        return this.mProxy.putDouble(key, value);
    }

    public boolean putSerializable(String key, Serializable serializable) {
        return this.mProxy.putSerializable(key, serializable);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.mProxy.getBoolean(key, defValue);
    }

    public <T extends Serializable> T getSerializable(String key, Class<T> clazz, T defValue) {
        return this.mProxy.getSerializable(key, clazz, defValue);
    }

    public <T extends Serializable> T getSerializable(String key, T defValue) {
        return (T) this.mProxy.getSerializable(key, (Class)null, defValue);
    }

    public <T extends Serializable> T getSerializable(String key) {
        return (T) this.mProxy.getSerializable(key, (Class)null, (Serializable)null);
    }

    public byte[] getBytes(String key, byte[] defaultValue) {
        return this.mProxy.getBytes(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return this.mProxy.getInt(key, defaultValue);
    }

    public short getShort(String key, short defValue) {
        return this.mProxy.getShort(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return this.mProxy.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return this.mProxy.getFloat(key, defValue);
    }

    public double getDouble(String key, double defValue) {
        return this.mProxy.getDouble(key, defValue);
    }

    public String getString(String key, String defValue) {
        return this.mProxy.getString(key, defValue);
    }

    public boolean remove(String key) {
        return this.mProxy.remove(key);
    }

}