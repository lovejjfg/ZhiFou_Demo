package com.lovejjfg.zhifou.data;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.lovejjfg.zhifou.data.model.ContactBean;
import com.lovejjfg.zhifou.data.model.SearchResult;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

/**
 * Created by 张俊 on 2016/2/21.
 */
public class BmobUtil {
    private static final String URL_INSERT = "https://api.bmob.cn/1/classes/info";
    private static final String URL_CONTACT = "https://api.bmob.cn/1/classes/Contacts";
    private static final String APPLICATION_ID = "f090e25bef0697ae9a8d2f06d08c0dad";
    private static final String API_KEY = "b55f225809b92ba6093a2b69a39f38f8";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final Gson gson = new Gson();
    private static final OkHttpClient client = new OkHttpClient();

    public static void sendTest(Context context, Callback callback) {
        try {
            SSLSocketFactory sslSocketFactory = SSLUtil.getSSLSocketFactory(context.getAssets().open("card.cer"));
            client.setSslSocketFactory(sslSocketFactory);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", e.toString());
        }
        Person person = new Person("张三", "杭州");
        RequestBody body = RequestBody.create(JSON, gson.toJson(person));

        Request insert = new Request.Builder()
                .url(URL_INSERT)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Bmob-Application-Id", APPLICATION_ID)
                .addHeader("X-Bmob-REST-API-Key", API_KEY)
                .post(body)
                .build();
        try {
            Response execute = client.newCall(insert).execute();
            client.newCall(insert).enqueue(callback);
            Log.e("result::",execute.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", e.toString());
        }

    }

    public static List<SearchResult> getSearchResult(String url) {
        Request insert = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            String re = client.newCall(insert).execute().body().string();
            if (TextUtils.isEmpty(re)) {
                return null;
            }
            String result = Html.fromHtml(Html.fromHtml(re).toString()).toString();
            List<SearchResult> newsList = new ArrayList<>();
            Gson gson = new Gson();

            if (!TextUtils.isEmpty(result)) {
                JSONArray resultArray = null;
                try {
                    resultArray = new JSONArray(result);


                    if (resultArray.length() == 0) {
                        return null;
                    } else {
                        JSONObject newsObject;
                        for (int i = 0; i < resultArray.length(); i++) {
                            newsObject = resultArray.getJSONObject(i);
                            SearchResult.ContentEntity entity = gson.fromJson(newsObject.getString("content"), SearchResult.ContentEntity.class);
                            SearchResult news = gson.fromJson(newsObject.getString("content"), SearchResult.class);

                            assert news != null;
                            news.setDate(newsObject.getString("date"));
                            news.setContent(entity);
                            newsList.add(news);
                        }
                        return newsList;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    public static void sendContact(Context context, ContactBean bean, Callback callback) {
        try {
            SSLSocketFactory sslSocketFactory = SSLUtil.getSSLSocketFactory(context.getAssets().open("card.cer"));
            client.setSslSocketFactory(sslSocketFactory);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", e.toString());
        }
        RequestBody body = RequestBody.create(JSON, gson.toJson(bean));
        Request insert = new Request.Builder()
                .url(URL_INSERT)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Bmob-Application-Id", APPLICATION_ID)
                .addHeader("X-Bmob-REST-API-Key", API_KEY)
                .post(body)
                .build();
        try {
            Response execute = client.newCall(insert).execute();
            client.newCall(insert).enqueue(callback);
            Log.e("result::",execute.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", e.toString());
        }

    }


    public static void getContacts(Context context, Callback callback) {
        try {
            SSLSocketFactory sslSocketFactory = SSLUtil.getSSLSocketFactory(context.getAssets().open("card.cer"));
            client.setSslSocketFactory(sslSocketFactory);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", e.toString());
        }
        Request insert = new Request.Builder()
                .url(URL_INSERT)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Bmob-Application-Id", APPLICATION_ID)
                .addHeader("X-Bmob-REST-API-Key", API_KEY)
                .get()
                .build();
        try {
            Response execute = client.newCall(insert).execute();
            client.newCall(insert).enqueue(callback);
            Log.e("result::", execute.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", e.toString());
        }
    }
}
