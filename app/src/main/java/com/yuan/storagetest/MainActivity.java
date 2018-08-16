package com.yuan.storagetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.yuan.storagetest.R;
import com.yuan.storage.Store;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Store.put("zhangsan", new TestBean());
        Store.put("string", "string");
        Store.put("int", 100);
        Store.put("long", 100L);
        Store.put("byte", (byte) 100);
        Store.put("short", (short) 100);
        Store.put("float", (float) 100);
        Store.put("boolean", true);
        ArrayList<TestBean> list = new ArrayList<>();
        list.add(new TestBean());
        list.add(new TestBean());
        list.add(new TestBean());
        Store.put("listsss", list);

        ArrayList<TestBean> listsss = Store.get("listsss", ArrayList.class, null);
        TestBean testBean = listsss.get(1);


        String s = Store.get("zhangsan", TestBean.class, null).toString() + "\n" +
                Store.get("string", "") + "\n"
                + Store.get("int", 0) + "\n" +
                Store.get("long", 0L) + "\n" +
                Store.get("byte", (byte) 0) + "\n"
                + Store.get("short", (short) 0) + "\n" +
                Store.get("float", (float) 0) + "\n" +
                Store.get("boolean", true) + "\n"
                + "\n" + testBean.toString();


        TextView tvShow = findViewById(R.id.tv_show);
        tvShow.setText(s);


    }

    public static class TestBean implements Serializable {
        public String name = "张三";
        public int age = 23;
        public boolean sex = true;

        @Override
        public String toString() {
            return "[name = " + name + ",age = " + age + ",sex = " + sex + "]";
        }
    }

}
