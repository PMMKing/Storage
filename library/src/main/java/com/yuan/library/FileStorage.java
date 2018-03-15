package com.yuan.library;

import android.content.Context;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by shucheng.qu on 2018/3/14
 */

public class FileStorage implements IStorage {

    private File file;
    private JSONObject jsonObject;

    public static IStorage newInstance(Context context, File file) {
        return new FileStorage(context, file);
    }

    private FileStorage(Context context, File file) {
        if (file == null) {
            new RuntimeException("file is null!");
        }

        this.file = file;
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            this.jsonObject = new JSONObject();
        } else {
            if (file.exists() && file.isDirectory()) {
                throw new RuntimeException("无法创建文件!已存在同名的文件夹!");
            }

            this.jsonObject = this.getJSONObject();
        }

    }

    private JSONObject getJSONObject() {
        JSONObject jsonObject = null;
        if (this.file.exists()) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            FileInputStream fis = null;

            try {
                byte[] e = new byte[4096];
                boolean len = true;
                fis = new FileInputStream(this.file);

                int len1;
                while ((len1 = fis.read(e)) != -1) {
                    out.write(e, 0, len1);
                }

                jsonObject = new JSONObject(new String(out.toByteArray(), "UTF-8"));
            } catch (Exception var14) {
                ;
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException var13) {
                        ;
                    }
                }

            }
        }

        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }

        return jsonObject;
    }

    private void saveJsonObject(JSONObject jsonObject) {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(this.file);
            byte[] e = jsonObject.toString().getBytes("UTF-8");
            fos.write(e);
        } catch (Exception var12) {
            ;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException var11) {
                    ;
                }
            }

        }

    }

//    private boolean put(String key, String value) {
//        try {
//            String v = Base64.encodeToString(value.getBytes(), 2);
//            synchronized (this.file) {
//                this.jsonObject.put(key, v);
//                this.saveJsonObject(this.jsonObject);
//            }
//            return true;
//        } catch (Throwable var6) {
//            return false;
//        }
//    }

    private boolean put(String key, byte[] value) {
        try {
            String v = Base64.encodeToString(value, 2);
            synchronized (this.file) {
                this.jsonObject.put(key, v);
                this.saveJsonObject(this.jsonObject);
            }
            return true;
        } catch (Throwable var6) {
            return false;
        }
    }

    private byte[] get(String key) throws JSONException {
        synchronized (this.file) {
            String value = this.jsonObject.optString(key, (String) null);
            byte[] decode = Base64.decode(value, 2);
            return decode;
        }
    }

    private static byte[] i2b(int i) {
        return new byte[]{(byte) (i >> 24 & 255), (byte) (i >> 16 & 255), (byte) (i >> 8 & 255), (byte) (i & 255)};
    }

    private static byte[] l2b(long value) {
        return new byte[]{(byte) ((int) (value >> 56 & 255L)), (byte) ((int) (value >> 48 & 255L)), (byte) ((int) (value >> 40 & 255L)), (byte) ((int) (value >> 32 & 255L)), (byte) ((int) (value >> 24 & 255L)), (byte) ((int) (value >> 16 & 255L)), (byte) ((int) (value >> 8 & 255L)), (byte) ((int) (value & 255L))};
    }

    private static int b2i(byte[] bytes) {
        int value = 0;

        for (int i = 0; i < 4; ++i) {
            int shift = (3 - i) * 8;
            value += (bytes[i] & 255) << shift;
        }

        return value;
    }

    private static long b2l(byte[] b) {
        long result = 0L;

        for (int i = 0; i < 8; ++i) {
            int shift = (7 - i) * 8;
            result += ((long) b[i] & 255L) << shift;
        }

        return result;
    }


    @Override
    public boolean putSerializable(String key, Serializable value) {
        ByteArrayOutputStream bytestream = null;
        ObjectOutputStream objectstream = null;
        boolean var7;
        try {
            bytestream = new ByteArrayOutputStream();
            objectstream = new ObjectOutputStream(bytestream);
            objectstream.writeObject(value);
            objectstream.flush();
            return this.put(key, bytestream.toByteArray());
        } catch (IOException var21) {
            var7 = false;
        } finally {
            if (objectstream != null) {
                try {
                    objectstream.close();
                } catch (IOException var20) {
                    ;
                }
            }
            if (bytestream != null) {
                try {
                    bytestream.close();
                } catch (IOException var19) {
                    ;
                }
            }

        }
        return var7;
    }

    @Override
    public boolean putBytes(String key, byte[] value) {
        return this.put(key, value);
    }

    @Override
    public boolean putInt(String key, int value) {
        return this.put(key, i2b(value));
    }

    @Override
    public boolean putShort(String key, short value) {
        byte[] result = new byte[]{(byte) (value >> 8 & 255), (byte) (value & 255)};
        return this.put(key, result);
    }

    @Override
    public boolean putLong(String key, long value) {
        byte[] result = l2b(value);
        return this.put(key, result);
    }

    @Override
    public boolean putFloat(String key, float value) {
        byte[] result = i2b(Float.floatToIntBits(value));
        return this.put(key, result);
    }

    @Override
    public boolean putDouble(String key, double value) {
        byte[] result = l2b(Double.doubleToLongBits(value));
        return this.put(key, result);
    }

    @Override
    public boolean putString(String key, String value) {
        return this.put(key, value.getBytes());
    }

    @Override
    public boolean putBoolean(String key, boolean value) {
        byte b = (byte)(value?1:0);
        byte[] data = new byte[]{b};
        return this.put(key, data);
    }

    @Override
    public <T extends Serializable> T getSerializable(String key, Class<T> clazz, T defaultValue) {
        Object bytestream = null;
        ObjectInputStream objectstream = null;
        Serializable result = defaultValue;
        try {
            byte[] e = this.get(key);
            if (e != null) {
                objectstream = new ObjectInputStream(new ByteArrayInputStream(e));
                result = (Serializable) objectstream.readObject();
            }
        } catch (Throwable var20) {
            ;
        } finally {
            if (objectstream != null) {
                try {
                    objectstream.close();
                } catch (IOException var19) {
                    ;
                }
            }

            if (bytestream != null) {
                try {
                    ((ByteArrayInputStream) bytestream).close();
                } catch (IOException var18) {
                    ;
                }
            }

        }

        return (T) result;
    }

    @Override
    public int getInt(String key, int defaul) {
        int result = defaul;

        try {
            byte[] e = this.get(key);
            if(e != null) {
                result = b2i(e);
            }
        } catch (Throwable var5) {
            ;
        }

        return result;
    }

    @Override
    public double getDouble(String key, double defaul) {
        double result = defaul;
        try {
            byte[] e = this.get(key);
            if (e != null) {
                result = Double.longBitsToDouble(b2l(e));
            }
        } catch (Throwable var7) {
            ;
        }
        return result;
    }

    @Override
    public float getFloat(String key, float defaul) {
        float result = defaul;
        try {
            byte[] e = this.get(key);
            if (e != null) {
                result = Float.intBitsToFloat(b2i(e));
            }
        } catch (Throwable var5) {
            ;
        }
        return result;
    }

    @Override
    public short getShort(String key, short defaul) {
        short result = defaul;
        try {
            byte[] e = this.get(key);
            if (e != null) {
                result = 0;
                for (int i = 0; i < 2; ++i) {
                    int shift = (1 - i) * 8;
                    result = (short) (result + ((e[i] & 255) << shift));
                }
            }
        } catch (Throwable var7) {
            ;
        }
        return result;
    }

    @Override
    public long getLong(String key, long defaul) {
        long result = defaul;
        try {
            byte[] e = this.get(key);
            if (e != null) {
                result = b2l(e);
            }
        } catch (Throwable var7) {
            ;
        }
        return result;
    }

    @Override
    public String getString(String key, String defaul) {
        String result = defaul;
        try {
            byte[] e = get(key);
            if (e != null) {
                result = new String(e, "UTF-8");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean getBoolean(String key, boolean defaul) {
        boolean result = defaul;
        try {
            byte[] e = this.get(key);
            if (e != null) {
                result = e[0] == 1;
            }
        } catch (Throwable var5) {
            ;
        }
        return result;
    }

    @Override
    public byte[] getBytes(String key, byte[] defaul) {
        try {
            byte[] e = this.get(key);
            if (e == null || e.length == 0) {
                e = defaul;
            }
            return e;
        } catch (Throwable var4) {
            return defaul;
        }
    }

    @Override
    public boolean remove(String key) {
        try {
            synchronized (this.file) {
                Object oldValue = this.jsonObject.remove(key);
                if (oldValue != null) {
                    this.saveJsonObject(this.jsonObject);
                }
                return oldValue != null;
            }
        } catch (Exception var6) {
            return false;
        }
    }

    @Override
    public boolean contains(String key) {
        synchronized (this.file) {
            boolean var10000;
            try {
                var10000 = this.jsonObject.has(key);
            } catch (Exception var5) {
                return false;
            }
            return var10000;
        }
    }
}