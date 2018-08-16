# Storage
android 本地化存储

````
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

dependencies {
	        implementation 'com.github.PMMKing:Storage:1.1.0'
	}
````

````
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Store.init(this,"testFile");
    }
}
````

````
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
````
