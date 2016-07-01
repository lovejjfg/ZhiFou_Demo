package com.lovejjfg.zhifou.data;

import okhttp3.Request;

/**
 * Created by Joe on 2016-05-27
 * Email: zhangjun166@pingan.com.cn
 */
public class RequestUtils {
    private static final String APPLICATION_ID = "f090e25bef0697ae9a8d2f06d08c0dad";
    private static final String API_KEY = "b55f225809b92ba6093a2b69a39f38f8";

    private static final String TYPE_TEXT = "text/plain";
    private static final String TYPE_JSON = "application/json";
    public static final String TYPE_IMAGE = "image/jpeg";

    public static Request createImageRequest(Request request) {
        return request.newBuilder()
                .addHeader("Content-Type", TYPE_IMAGE)
                .addHeader("X-Bmob-Application-Id", APPLICATION_ID)
                .addHeader("X-Bmob-REST-API-Key", API_KEY)
                .build();
    }

    public static Request createJsonRequest(Request request) {
        return request.newBuilder()
                .addHeader("Content-Type", TYPE_JSON)
                .addHeader("X-Bmob-Application-Id", APPLICATION_ID)
                .addHeader("X-Bmob-REST-API-Key", API_KEY)
                .build();
    }
    public static Request createJustJsonRequest(Request request) {
        return request.newBuilder()
                .addHeader("Content-Type", TYPE_JSON)
                .build();
    }

    public static Request createTextRequest(Request request) {
        return request.newBuilder()
                .addHeader("Content-Type", TYPE_TEXT)
                .addHeader("X-Bmob-Application-Id", APPLICATION_ID)
                .addHeader("X-Bmob-REST-API-Key", API_KEY)
                .build();
    }
}
