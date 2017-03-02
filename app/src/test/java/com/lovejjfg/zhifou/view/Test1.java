package com.lovejjfg.zhifou.view;

import android.os.SystemClock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lovejjfg.zhifou.base.Utils;
import com.lovejjfg.zhifou.data.Person;
import com.lovejjfg.zhifou.util.RSAUtils;

import junit.framework.Assert;

import org.codehaus.plexus.util.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
    public void testRSAPair() throws Exception {
        KeyPair keyPair = RSAUtils.generateRSAKeyPair(1024);
        String priFormat = keyPair.getPrivate().getFormat();
        System.out.println(priFormat);
        String pubFormat = keyPair.getPublic().getEncoded().toString();
        System.out.println(pubFormat);
    }

    @Test
    public void testRSA() throws Exception {
        KeyPair keyPair = RSAUtils.generateRSAKeyPair(1024);
        KeyPair keyPair2 = RSAUtils.generateRSAKeyPair(1024);
        String s1 = initPubKey(PUCLIC_KEY);
        PublicKey publicKey = keyPair.getPublic();// RSAUtils.loadPublicKey("X.509");
        PrivateKey privateKey = keyPair.getPrivate();// RSAUtils.loadPrivateKey("PKCS#8");
//        PublicKey publicKey = RSAUtils.loadPublicKey("xxxxxxx");
        byte[] bytes = RSAUtils.encryptData((HASH + "886520").getBytes(), publicKey);
//        String encode = Base64Utils.encode(bytes);
        bytes = Base64.encodeBase64(bytes);
        System.out.println("最后加密结果：" + new String(bytes));
//        String decode = URLDecoder.decode("%E5%8F%B9%E6%9C%8D", "UTF-8");
//        System.out.println("decode::" + decode);
        byte[] decryptData = RSAUtils.decryptData(Base64.decodeBase64(bytes), privateKey);
        System.out.println("最后解密结果：" + new String(decryptData));

    }

    @Test
    public void testDownload() throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://raw.githubusercontent.com/lovejjfg/screenshort/master/loading.png")
                .build();
        Call call = client.newCall(request);
        Response execute = call.execute();
        ResponseBody body = execute.body();
        InputStream inputStream = body.byteStream();
        File file = new File("E:\\loading.png");
        if (file.exists() && file.length() > 0) {
            long skip = file.length();
            System.out.println("文件的length:" + skip);
            long skip1 = inputStream.skip(skip);
            System.out.println("跳过字节：" + skip1);

        }
        FileOutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int read = -1;
        while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
            System.out.println("读取了:" + read);
            SystemClock.sleep(1000);
            outputStream.write(buffer, 0, read);
//            call.cancel();
        }
        outputStream.close();
        inputStream.close();
    }

    @Test
    public void testDownload2() throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("RANGE", "bytes=0-999")
//                .header("RANGE", "bytes=1000")
                .url("https://raw.githubusercontent.com/lovejjfg/screenshort/master/Blog2.gif")
                .build();
        Call call = client.newCall(request);
        File file = new File("E:\\Blog2.gif");
        Response execute = call.execute();
        long timeMillis = System.currentTimeMillis();
//        ResponseBody body = execute.body();
//        long contentLength = body.contentLength();
//        int length = body.bytes().length;
//        System.out.printf("contentLength:%d%n", contentLength);
//        System.out.printf("body.bytes().length:%d%n", length);
        save(file, execute, 0);
//        save(file, execute, 1000);
        long l = System.currentTimeMillis() - timeMillis;
        System.out.println("一共用时：毫秒::" + l);

    }

    private void save(File destination, Response response, long startsPoint) {
        ResponseBody body = response.body();
        InputStream in = body.byteStream();
        long contentLength = body.contentLength();
        System.out.printf("contentLength:%d%n", contentLength);
        FileChannel channelOut = null;
        // 随机访问文件，可以指定断点续传的起始位置
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(destination, "rwd");
            //Chanel NIO中的用法，由于RandomAccessFile没有使用缓存策略，直接使用会使得下载速度变慢，亲测缓存下载3.3秒的文件，用普通的RandomAccessFile需要20多秒。
            channelOut = randomAccessFile.getChannel();
            // 内存映射，直接使用RandomAccessFile，是用其seek方法指定下载的起始位置，使用缓存下载，在这里指定下载位置。
            MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, startsPoint, body.contentLength());
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                mappedBuffer.put(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}