package com.lovejjfg.zhifou.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lovejjfg.zhifou.base.Utils;
import com.lovejjfg.zhifou.data.Person;
import com.lovejjfg.zhifou.util.Base64Utils;
import com.lovejjfg.zhifou.util.RSAUtils;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Joe on 2016/12/21.
 * Email lovejjfg@gmail.com
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 23, manifest = Config.NONE)
public class Test1 {


    private static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdScM09sZJqFPX7bvmB2y6i08J\nbHsa0v4THafPbJN9NoaZ9Djz1LmeLkVlmWx1DwgHVW+K7LVWT5FV3johacVRuV98\n37+RNntEK6SE82MPcl7fA++dmW2cLlAjsIIkrX+aIvvSGCuUfcWpWFy3YVDqhuHr\nNDjdNcaefJIQHMW+sQIDAQAB";
    private static String HASH = "d0597c3485befc30";


    @Before
    public void setUp() throws Exception {

    }

    public <T> void onBackPressed(List<T> list) throws Exception {
        Gson gson = new Gson();
        String str = gson.toJson(list);
        System.out.println(str);
        List<T> ps = gson.fromJson(str, new TypeToken<List<T>>() {
        }.getType());
        for (int i = 0; i < ps.size(); i++) {
            T p = ps.get(i);
            System.out.println(p.toString());
        }
    }

    @Test
    public void onClick() throws Exception {
        Person p = new Person("xxxxx", "ooooo");
        Person p1 = new Person("xxxxx1", "ooooo1");
        Person p2 = new Person("xxxxx2", "ooooo2");
        Person p3 = new Person("xxxxx3", "ooooo3");
        ArrayList<Person> list = new ArrayList<>();
        list.add(p);
        list.add(p1);
        list.add(p2);
        list.add(p3);
//        List<Themes> list = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            Themes themes = new Themes();
//            list.add(themes);
//        }
        onBackPressed(list);
    }

    @Test
    public void onItemClick() throws Exception {

        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            if (i == 3) {
                break;
            }
        }
        System.out.println("over");
    }

    @Test
    public void getRawType() throws Exception {

        List<String> list = new ArrayList<>();
        Class<?> rawType = Utils.getRawType(list.getClass());
        System.out.println(rawType.getName());
        Assert.assertTrue(List.class.isAssignableFrom(list.getClass()));

    }

    @Test
    public void getParameterUpperBound() throws Exception {
        List<String> list = createProxy(List.class);
        boolean xxxxx = list.add("xxxxx");
        System.out.println(xxxxx);

    }

    @Test
    public void testBaseModel() throws Exception {
//        final CountDownLatch signal = new CountDownLatch(1);
//        // https://raw.githubusercontent.com/lovejjfg/ZhiFou_Demo/master/BaseModel.json
//        BaseDataManager.handleService(BaseDataManager.getDailyApiService().getList("https://raw.githubusercontent.com/lovejjfg/ZhiFou_Demo/master/BaseModel.json")
//                , dailyStories -> {System.out.println(dailyStories.toString());
//                    signal.countDown();
//                }, throwable -> {System.out.println(throwable.toString());
//                    signal.countDown();
//                });
//        signal.await();

    }

    private <T> T createProxy(Class<T> tClass) {
        //noinspection unchecked
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                Class<?> rawType = Utils.getRawType(tClass);
                System.out.println(rawType.getName());
                System.out.println(method.getName());
                System.out.println(Arrays.asList(args).toString());
                return true;
            }
        });
    }

    public String initPubKey(String paramString) {
        return paramString.replaceFirst("-----BEGIN PUBLIC KEY-----\n", "").replace("\n-----END PUBLIC KEY-----\n", "");
    }

    @Test
    public void testRSA() throws Exception {
        String s1 = initPubKey(PUCLIC_KEY);
        System.out.println("公钥：" + s1);
        PublicKey publicKey = RSAUtils.loadPublicKey(PUCLIC_KEY);
//        PublicKey publicKey = RSAUtils.loadPublicKey("xxxxxxx");
        byte[] bytes = RSAUtils.encryptData((HASH + "886520").getBytes(), publicKey);
        String encode = Base64Utils.encode(bytes);
        System.out.println("最后加密结果：" + encode);
    }


}